package net.JournalApp.repository;

import net.JournalApp.entity.ConfigJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJurnalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
