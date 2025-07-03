package net.engineeringdigest.journalApp.service;

import com.mongodb.assertions.Assertions;
import net.engineeringdigest.JournalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.JournalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    public void testmail(){
        boolean result = emailService.sendEmail("nobitasizu91@gmail.com", "Mail sending test",
                "This is the test main sent through spring boot application ");
        Assertions.assertTrue(result);
    }

}
