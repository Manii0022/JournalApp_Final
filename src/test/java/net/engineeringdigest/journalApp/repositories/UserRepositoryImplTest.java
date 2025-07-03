package net.engineeringdigest.journalApp.repositories;

import net.engineeringdigest.JournalApp.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTest {
    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    public void testusers(){
        Assertions.assertNotNull(userRepository.getUsersForSA());

    }

}
