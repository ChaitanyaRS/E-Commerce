package com.ecommerce.orderservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.orderservice.entity.Idempotency;

@Repository
public interface IdempotencyRepo extends MongoRepository<Idempotency,String> {

}
