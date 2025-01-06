package com.ecommerce.productservice.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.productservice.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {

    Category findByCategoryName(String category);

}
