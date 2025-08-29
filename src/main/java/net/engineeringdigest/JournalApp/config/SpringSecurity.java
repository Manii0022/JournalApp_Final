package net.engineeringdigest.JournalApp.config;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.filter.JwtFilter;
import net.engineeringdigest.JournalApp.repository.UserRepository;
import net.engineeringdigest.JournalApp.service.UserDetailsServiceImpl;
import net.engineeringdigest.JournalApp.service.UserService;
import net.engineeringdigest.JournalApp.utils.JwtUtil;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {     // webSecurityConfigurerAdapter is deprecated , use SecurityFilterChain instead

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/journal/**", "/user/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")  // Only users with the role "ADMIN" can access URLs matching /admin/**.
                .anyRequest().permitAll();


        http.oauth2Login(oauth2 -> oauth2
                .successHandler((request, response, authentication) -> {
                    // extract user details
                    var oauthUser = (org.springframework.security.oauth2.core.user.OAuth2User) authentication.getPrincipal();

                    String email = oauthUser.getAttribute("email");
                    Optional<User> existingUser = userRepository.findByEmail(email);

                    try {

                        if (existingUser.isPresent()) {
                            String jwt = jwtUtil.generateToken(email);
                            response.sendRedirect(
                                    "http://localhost:5173/dashboard?exists=true&email=" + email + "token=" + jwt + "&name=" + oauthUser.getAttribute("name")
                            );
                        }
                        else {
                            User user = new User();
                            user.setUserName(oauthUser.getAttribute("name"));
                            user.setEmail(oauthUser.getAttribute(email));
                            user.setPassword(GeneratePass());
                            userService.saveNewUser(user);

                            String jwt = jwtUtil.generateToken(email);
                            response.sendRedirect(
                                    "http://localhost:5173/dashboard?token=" + jwt + "&email=" + email + "&name=" + oauthUser.getAttribute("name")
                            );
                        }
                    } catch (Exception e) {
                        System.out.println(e + "Exception occured");
                    }
                })

        );

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // spring security , session manage krta hai .. isiliye usey disable krdiyia
        http.csrf().disable();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:5173"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(List.of("*"));
                    return config;
                }));
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

    @Bean
    public String GeneratePass() {
        String code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyx1234567890!@#$%^&*/";
        String pass= "";
        for (int i = 0; i < 10; i++) {
            int num = (int) (Math.random() * code.length() + 0);
            pass = pass + code.charAt(num);
        }
        return pass;
    }

}
