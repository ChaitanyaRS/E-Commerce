package com.ecommerce.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;
// import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Service;

import com.ecommerce.orderservice.entity.Counter;
import com.mongodb.client.model.UpdateOptions;

@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoTemplate;

    public int generateSequence(String seqName) {
        // Query to find the document with the given seqName
        Query query = new Query(Criteria.where("_id").is(seqName));

        // Update the seq field to increment by 1
        Update update = new Update().inc("seq", 1);

        // // Options to return the updated document and upsert if it doesn't exist
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

        // Find and modify the counter
        Counter counter = mongoTemplate.findAndModify(query, update, options, Counter.class);

        return counter != null ? counter.getSeq() : 1;
    }
}
