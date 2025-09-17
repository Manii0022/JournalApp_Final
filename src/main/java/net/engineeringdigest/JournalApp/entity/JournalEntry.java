package net.engineeringdigest.JournalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.engineeringdigest.JournalApp.enums.Mood;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// @Document-->  this means ki JournalEntry ka instance , ek document(row) k barabar hoga
// "journal_entries" --> this is collection(table) name .... if not given then class k naam se table bn jaati hai

@Document(collection="journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id    // iski help se id ko as primary key map kr payenge (zaroori bhi nhi hai dena ..can skip as well)
    public String id;

    @NonNull
    public String title;

    public String content;

    private LocalDateTime date;

    private String mood;

}
