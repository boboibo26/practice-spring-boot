package com.api.API.repository;

import com.api.API.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {


    List<Product> findByName(String name);


}
