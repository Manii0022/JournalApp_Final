package net.engineeringdigest.JournalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.JournalApp.dto.UserDTO;
import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.service.UserService;
import net.engineeringdigest.JournalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;    // AuthenticationManager use kiya hai isiliye iski bean bnani padegi in spring config class

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "ok";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody UserDTO user) {
        User newUser = new User();           // DTO object se User entity object me data daalne k liye
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        newUser.setPassword(user.getPassword());
        userService.saveNewUser(newUser);     // saveNewUser() User class ka object expect krta hai , thus newUser bna rhe hai which is of User entity type
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {    // this is the implementation of the manual authentication that was defined in SpringSecurity class
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

//            Directly get the authenticated UserDetails (no second DB call)
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // this calls DB again
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password ", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/home")
    public String home() {
        return "you are on home";

    }

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal org.springframework.security.oauth2.core.user.OAuth2User oauthUser) {
        // directly get attributes from Google
//        return oau.getAttributes();

        System.out.println("User Name: " + oauthUser.getAttribute("name"));
        System.out.println("Email: " + oauthUser.getAttribute("email"));
        System.out.println("Picture: " + oauthUser.getAttribute("picture"));

        return "User Name: ";

    }
}
