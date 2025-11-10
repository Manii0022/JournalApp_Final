package net.engineeringdigest.JournalApp.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

// @Document-->  this means ki JournalEntry ka instance , ek document(row) k barabar hoga
// "users" --> this is collection(table) name .... if not given then class k naam se table bn jaati hai

@Document(collection="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    @Indexed(unique = true)           //@Indexed → Creates an index in MongoDB for faster searches.
                                        // unique = true → Ensures no two users can have the same userName (like a primary key).
    @NonNull
    private String userName;
    @NonNull
    private String password;

    private String email;
    private boolean getUpdates;   // get automatic updates via email

    @DBRef            // like a foreign key used to reference another document
    private List<JournalEntry> journalEntries=new ArrayList<>();
    private List<String> roles;
}
