package net.engineeringdigest.journalApp.Scheduler;

import com.mongodb.assertions.Assertions;
import net.engineeringdigest.JournalApp.Scheduler.UserScheduler;
import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.JournalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserSchedulerTest {


    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void schedulerTest(){
        userScheduler.fetchUsersAndSendSaMails();
    }

}
