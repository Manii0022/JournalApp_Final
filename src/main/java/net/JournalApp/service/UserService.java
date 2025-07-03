package net.JournalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.JournalApp.entity.User;
import net.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j         // automatic instance of logger (log)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();     // PasswordEncoder ka implementation hai BCryptPasswordEncoder()
    // bhot saare encoders hote hai, unme se ek hai BCryptPasswordEncoder()

    // private static final Logger logger= LoggerFactory.getLogger(UserService.class);          // manual instance

    public void saveUser(User user){
            userRepository.save(user);
    }

    public void saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));   // user k password me encoded password set krdiya
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);          //  and then db me save krwa rhe hai
        }
        catch (Exception e) {
            log.error("erro hai ji ");
            log.warn("warn hai ji");
            log.info("info hai ji ");        // log for Slf4j .... logger for manual instance
            log.debug("debug hai ji");
            log.trace("trace hai ji");
        }
    }

    public void saveNewAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    public  List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }


    public void updateById(ObjectId id){

        // updation code is directly written in the controller class
    }


}
