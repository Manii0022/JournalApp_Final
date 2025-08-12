package net.engineeringdigest.JournalApp.Scheduler;

import net.engineeringdigest.JournalApp.entity.JournalEntry;
import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.enums.Sentiment;
import net.engineeringdigest.JournalApp.model.SentimentData;
import net.engineeringdigest.JournalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.JournalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

//    @Scheduled(cron="0 0 9 * * SUN")
//    @Scheduled(cron = "0/30 * * * * *")
//    public void fetchUsersAndSendSaMails(){
//
//        List<User> users=userRepository.getUsersForSA();
//        for(User user:users){
//            List<JournalEntry> journalEntries=user.getJournalEntries();
//            List<Sentiment> sentiments=journalEntries.stream().filter(x-> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x-> x.getSentiment()).collect(Collectors.toList());
//            Map<Sentiment,Integer>sentimentCounts=new HashMap<>();
//            for(Sentiment sentiment:sentiments){
//                if(sentiment!=null){
//                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
//                }
//            }
//            Sentiment mostFrequentSentiment=null;
//            int maxCount=0;
//            for(Map.Entry<Sentiment,Integer> entry:sentimentCounts.entrySet()){
//                if(entry.getValue()>maxCount){
//                    maxCount= entry.getValue();
//                    mostFrequentSentiment=entry.getKey();
//                }
//            }
//            if(mostFrequentSentiment !=null){
//                    SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days "+mostFrequentSentiment).build();
//
//                try{   // this try-catch is for situation when kafka does not work... fir directly main bhej rhe hai
//                    kafkaTemplate.send("weekly-sentiments",sentimentData.getEmail(),sentimentData);
//
//                }
//                catch(Exception e){
//                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
//                }
//            }
//        }
//    }
}
