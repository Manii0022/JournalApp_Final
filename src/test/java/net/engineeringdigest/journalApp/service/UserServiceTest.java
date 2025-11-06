package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Disabled                 // jb koi test run nhi krna ho
//    @Test
//    public void testFindByUsername(){        // this is one test
//        assertEquals(4,2+2);
//        assertNotNull( userRepository.findByUserName("manish"));    // this is not the test ,, just the condition
//        assertTrue(5>3);
//    }
//
//    @Disabled
//    @Test
//    public void testJournalEntriesPresent(){      // this is another test
//        User user=userRepository.findByUserName("manish");
//        assertTrue(!user.getJournalEntries().isEmpty());
//    }
//
//    @Disabled
//    @ParameterizedTest
//    @CsvSource({           // these are now 3 tests inside a single test
//            "1,1,2",
//            "2,6,8",
//            "3,3,9"
//    })
//    public void testParameterized(int a, int b, int expected){
//        assertEquals(expected,a+b);
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "ram","manish","ghanshyam"
//    })
//    public void testChecckUsers(String name){
//        assertNotNull(userRepository.findByUserName(name),"failed for user "+name);   // msg will be displayed only for failed test cases
//    }
}
