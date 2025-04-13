package net.engineeringdigest.JournalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("config_journal_app")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {

    private String key;
    private String value;

}
