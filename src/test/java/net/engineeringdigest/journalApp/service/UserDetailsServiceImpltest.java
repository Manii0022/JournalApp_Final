package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.repository.UserRepository;
import net.engineeringdigest.JournalApp.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

//@SpringBootTest     1. poora application context nhi chalana isiliye hta diya
@ExtendWith(MockitoExtension.class)              // to initialize the @Mock objects... (new approach)
public class UserDetailsServiceImpltest {

//    //@Autowired            2. thus autowired bhi null ho jayega ...isiliye isey bhi hta diya
//    @InjectMocks            // lekin
//    private UserDetailsServiceImpl userDetailsServic;
//
//    @Mock   // repository ko mock krliya ..ie actual repo na chal k humari mock repo chle (yhi waali)
//    private UserRepository userRepository;
//
////    @BeforeEach
////    void setup() {
////        MockitoAnnotations.initMocks(this);             // to initialize the @Mock objects otherwise wo null rhenge.. (old approach)
////    }
//
//    @Disabled
//    @Test
//    public void loadUserByUsrnameTest(){
//        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("ag7f8gfg8a00").roles(new ArrayList<>()).build());
//        UserDetails user=userDetailsServic.loadUserByUsername("ram");
//        Assertions.assertNotNull(user);
//    }
}