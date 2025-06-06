package com.ecommerce.productservice.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "reviews")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rId;
    private int ratings;
    @OneToMany(mappedBy = "rating", orphanRemoval = true)
    @JsonBackReference
    private List<Product> productList;

    public void addProdToList(Product product) {
        this.productList.add(product);
    }
}
