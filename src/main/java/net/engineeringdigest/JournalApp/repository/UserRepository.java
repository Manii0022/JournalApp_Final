package net.engineeringdigest.JournalApp.repository;

import net.engineeringdigest.JournalApp.entity.JournalEntry;
import net.engineeringdigest.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
                                    // entity type(Collection/POJO) , Id ka type

    User findByUserName(String username);

   /* Custom Query Method: findByUserName(String username)

    Spring Data MongoDB automatically generates a query for this method.
    It translates to the MongoDB query:

    { "userName": "some_username" }

    Java field (userName) → Follows standard camelCase for variables.
    Java method (findByUserName) → Uses PascalCase for method names.

    Spring automatically converts method names to match the actual field names in the MongoDB document. (See more on chatgpt)
*/

    void deleteByUserName(String username);


}



