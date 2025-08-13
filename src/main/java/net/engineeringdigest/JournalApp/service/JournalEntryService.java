package net.engineeringdigest.JournalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.JournalApp.entity.JournalEntry;
import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry journalEntry,String username) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);   // pehla kaam done

            User user = userService.findByUserName(username);      // user get krliya
            user.getJournalEntries().add(saved);     // doosra kaam done ie. user ki journalEntries[] field me journalEntry ki _id save kra di
            // hume kaise pta ki sirf id hi store hogi [] me bcoz of DBRef.. wo bss reference store krta hai ie. _id

            userService.saveUser(user);   // list me add krne k baad wapas se save bhi toh krna hai user in db

        }
        catch (Exception e) {
//            System.out.println(e);
            throw new RuntimeException("an error occured while saving entry ");
        }
    }

    public void saveEntry(JournalEntry journalEntry ){    // this is just for updation
        journalEntryRepository.save(journalEntry);
    }

    public  List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username){
        boolean removed=false;
        try{

            User user=userService.findByUserName(username);   // pehle user ko find krliya
            removed=user.getJournalEntries().removeIf(x->x.getId().equals(id));   // user me journalEntries[] me se delete

        /*  x represents each element in the collection (in this case, each journal entry in user.getJournalEntries()).

        How do we know that?
                Because of the method it's used with: Since user.getJournalEntries() returns a list of JournalEntry objects,
                the lambda (x -> x.getId().equals(id)) must be operating on JournalEntry objects */

            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);    // journal_entries db me se bhi delete
            }

        }
        catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An Error occured while deleting the entry",e);
        }
        return removed;

    }

    public void updateById(ObjectId id){

        // updation code is directly written in the controller class
    }


}
