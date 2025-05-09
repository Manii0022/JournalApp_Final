package net.engineeringdigest.JournalApp.controller;

import net.engineeringdigest.JournalApp.cache.AppCache;
import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity getAllUsers(){
        List<User> all = userService.getAll();
        if(all !=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/create-admin-user")
    public ResponseEntity createNewAdmin(@RequestBody User user){
        userService.saveNewAdmin(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }


    @GetMapping("clear-app-cache")
    public void clearAppcache(){
        appCache.init();
    }

}