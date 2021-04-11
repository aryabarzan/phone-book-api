package com.aryabarzan.phonebook.repository;

import com.aryabarzan.phonebook.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends MongoRepository<Contact, Long> {
    @Query(value = "{'lastName':?0}")
    public Contact findByLastName(String lastName);
}
