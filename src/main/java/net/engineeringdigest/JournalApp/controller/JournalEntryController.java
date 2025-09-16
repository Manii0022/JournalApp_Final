package net.engineeringdigest.JournalApp.controller;

import net.engineeringdigest.JournalApp.entity.JournalEntry;
import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.service.JournalEntryService;
import net.engineeringdigest.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // jo bhi user createEntry ko call krega , uska name get kr rhe hai

        try{
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();  // yha pr tbhi ayenge na jb hum authenticate ho chuke honge
        String userName=authentication.getName();                       // authenticate hone k baad SecurityContextHolder se username fetch krliya
        User user =userService.findByUserName(userName);      // uss username se fir entries nikal li
        List<JournalEntry> all=user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return ResponseEntity.ok(all);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String myId){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

        User user=userService.findByUserName(username);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry=journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable(value="myId") String id){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        boolean removed=journalEntryService.deleteById(id,username);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJornalbyId(@PathVariable String id, @RequestBody JournalEntry newEntry)
    {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userService.findByUserName(username);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(id)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry=journalEntryService.findById(id);
            if(journalEntry.isPresent()){
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                old.setMood(newEntry.getMood() != null && !newEntry.getMood().equals("") ? newEntry.getMood() : old.getMood());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}


// call stack .....    controller --> service --> Repository
// service me business logic hai
// repository me db se query chalate hai ... isiliye bss extend krte hai MongoRepository