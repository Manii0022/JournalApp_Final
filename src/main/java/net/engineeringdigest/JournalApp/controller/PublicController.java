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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody UserDTO user){
        User newUser=new User();           // DTO object se User entity object me data daalne k liye
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        newUser.setPassword(user.getPassword());
        userService.saveNewUser(newUser);     // saveNewUser() User class ka object expect krta hai , thus newUser bna rhe hai which is of User entity type
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails=userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }
        catch(Exception e){
            log.error("Exception occurred while createAuthenticationToken ",e);
            return new ResponseEntity<>("Incorrect uername or password ",HttpStatus.BAD_REQUEST);
        }
    }

}
