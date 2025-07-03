package net.JournalApp.repository;

import net.JournalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUsersForSA(){
        Query query=new Query();

//        query.addCriteria(Criteria.where("email").exists(true));   // when we write seperate, it by default AND operator
//        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

        Criteria criteria=new Criteria();
        query.addCriteria(criteria.andOperator(             // this is for OR operator
//                Criteria.where("email").exists(true),   // ye dono lines valid email verify k liye thi... uski jagah pe niche regex use krliya
//                Criteria.where("email").ne(null).ne(""),
//                Criteria.where("roles").in("USER","ADMIN"),
                Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),
                Criteria.where("sentimentAnalysis").is(true))
        );

        List<User> users=mongoTemplate.find(query, User.class);               // to run the query in class User (bcos User me ek collection hai and uspe query chalegi)

        return users;
    }
}
