package com.ecommerce.productservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.productservice.entity.Rating;

@Repository
public interface RatingRepo extends JpaRepository<Rating,Integer> {

}
