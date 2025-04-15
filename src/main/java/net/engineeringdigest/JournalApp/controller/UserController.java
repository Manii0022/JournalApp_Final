package net.engineeringdigest.JournalApp.controller;

import net.engineeringdigest.JournalApp.api.response.WeatherResponse;
import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.repository.UserRepository;
import net.engineeringdigest.JournalApp.service.UserService;
import net.engineeringdigest.JournalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping()
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);

    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();

        /* jb koi user authenticate hota hai then uski info SecurityContextHolder me store hoti hai and isi se hum user ko
        extract krenge ie authentication.getName();    --> getName() se username ayega
        */

        User userInDb=userService.findByUserName(userName);    // fir whi normal updation ki process

        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    // this is delete by id

//    @DeleteMapping
//    public ResponseEntity<?> deleteUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();
//        User userInDb=userService.findByUserName(userName);
//        ObjectId id= userInDb.getId();
//        userService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


    // this is delete by username ... iske liye userRepository me deleteByUserName ka method dena padega

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("greet")
    public ResponseEntity<?> greeetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse=weatherService.getWeather("Mumbai");       // yha se getWeather ko call krliya--> ab service me jayenge
        String greeting="";
        if(weatherResponse!= null){
            greeting=", Weather feels like "+ weatherResponse.getCurrent().getTemperature();
        }
        return new ResponseEntity<>("hello : "+authentication.getName() + greeting,HttpStatus.OK);
    }
}

// call stack .....    controller --> service --> Repository
// service me business logic hai
// repository me db se query chalate hai ... isiliye bss extend krte hai MongoRepository