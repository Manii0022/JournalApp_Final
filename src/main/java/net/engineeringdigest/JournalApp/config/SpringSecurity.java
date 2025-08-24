package net.engineeringdigest.JournalApp.config;


import net.engineeringdigest.JournalApp.filter.JwtFilter;
import net.engineeringdigest.JournalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {     // webSecurityConfigurerAdapter is deprecated , use SecurityFilterChain instead

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/journal/**", "/user/**").authenticated()  // jo bhi /journal/... and /user/... se aane waali requests hai unhe authenticate kro bss
                .antMatchers("/admin/**").hasRole("ADMIN")  // Only users with the role "ADMIN" can access URLs matching /admin/**.
                .anyRequest().permitAll();                  // , baaki anyRequests ko simply permit krdo

//        http.oauth2Login(oauth2login -> oauth2login.successHandler(
//                new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        response.sendRedirect("/home");
//                    }
//                }
//        )).formLogin(Customizer.withDefaults());

        http.oauth2Login(oauth2 -> oauth2
                .successHandler((request, response, authentication) -> {
                    // extract user details
                    var oauthUser = (org.springframework.security.oauth2.core.user.OAuth2User) authentication.getPrincipal();

                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);

                    request.getSession(true).setAttribute(
                            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                            context);

                    // print details to terminal
                    System.out.println("User Name: " + oauthUser.getAttribute("name"));
                    System.out.println("Email: " + oauthUser.getAttribute("email"));
                    System.out.println("Picture: " + oauthUser.getAttribute("picture"));

                    // redirect after success
                    response.sendRedirect("/public/profile");
                })
        );

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();   // spring security , session manage krta hai .. isiliye usey disable krdiyia
        // csrf ko bhi disable krdiya to prevent cyber attacks

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // this is manual authentication for JWT
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
