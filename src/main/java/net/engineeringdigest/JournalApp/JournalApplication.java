package net.engineeringdigest.JournalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

    public static void main(String[] args) {

        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){  // name can be anything ie. add, falana, dimkana etc.
        return new MongoTransactionManager(dbFactory);
    }

    /*
    MongoDatabaseFactory --> helps to create connection with database

    */

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}