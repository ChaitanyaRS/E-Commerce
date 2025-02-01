package com.ecommerce.productservice.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.productservice.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT * FROM products p WHERE p.c_id = :cId", nativeQuery = true)
    List<Product> findByCId(int cId);

    @Query(value = "SELECT * FROM products p WHERE p.r_id = :rId", nativeQuery = true)
    List<Product> findByRId(int rId);

    @Query(value = "SELECT * FROM products p WHERE p.pid = :id", nativeQuery = true)
    Optional<Product> findByPId(int id);
    
}
