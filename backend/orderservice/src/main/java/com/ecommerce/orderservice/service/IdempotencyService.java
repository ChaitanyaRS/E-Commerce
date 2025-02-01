package com.ecommerce.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.orderservice.entity.Idempotency;
import com.ecommerce.orderservice.repo.IdempotencyRepo;

@Service
public class IdempotencyService {
    @Autowired
    private IdempotencyRepo idRepo;

    public boolean isDuplicate(String key){
        return idRepo.existsById(key);
    }
    
    public void saveIdempotencyKey(Idempotency id){
        idRepo.save(id);
    }

    public String getPreviousResponse(String key){
        return idRepo.findById(key).get().getResponse();
    }
}
