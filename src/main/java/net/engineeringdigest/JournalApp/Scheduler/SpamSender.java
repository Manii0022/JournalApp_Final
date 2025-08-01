package net.engineeringdigest.JournalApp.Scheduler;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.JournalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//@Component
//@Slf4j
//public class SpamSender {
//
//    @Autowired
//    private EmailService emailService;
//
////    @Scheduled(cron = "*/2 * * * * *") // every 10 seconds
//    public void sendSpamMails(){
//        try{
//            emailService.sendEmail("mananbhardwaj2705@gmail.com","kya haal Manan bhai","YE MAILS MERI APPLICATION SE AA RHE HAI ");
//        } catch (Exception e) {
//            log.error("could not send mail ",e);
//        }
//        log.info("mail sent successfully ");
//
//    }
//}
