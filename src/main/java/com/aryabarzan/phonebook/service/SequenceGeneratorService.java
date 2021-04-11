package com.aryabarzan.phonebook.service;

import com.aryabarzan.phonebook.model.DatabaseSequences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SequenceGeneratorService {
    private final MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {
        DatabaseSequences counter = mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("SeqValue", 1), FindAndModifyOptions.options().returnNew(true).upsert(true),
                DatabaseSequences.class);
        return !Objects.isNull(counter) ? counter.getSeqValue() : 1;

    }
}
