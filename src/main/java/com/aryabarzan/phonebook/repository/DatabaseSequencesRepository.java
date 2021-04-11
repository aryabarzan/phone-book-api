package com.aryabarzan.phonebook.repository;

import com.aryabarzan.phonebook.model.DatabaseSequences;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseSequencesRepository extends MongoRepository<DatabaseSequences, String> {
}
