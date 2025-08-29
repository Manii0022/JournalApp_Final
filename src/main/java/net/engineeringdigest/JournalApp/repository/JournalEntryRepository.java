package net.engineeringdigest.JournalApp.repository;

import net.engineeringdigest.JournalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {
}                                           // entity type(Collection/POJO) , Id ka type
