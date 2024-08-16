package com.api.API.controller;

import com.api.API.model.Product;
import com.api.API.model.ResponseObject;
import com.api.API.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {
    @Autowired
    private ProductRepository repository;


    // Get All Products
    @GetMapping("")
    List<Product> getAllProducts(){
         return  repository.findAll();
    }

    // Find Product by ID
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getProductById(@PathVariable String id){
        Optional<Product> foundProduct = repository.findById(id);

        return foundProduct.isPresent()?  ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK","Find product successfully", foundProduct)
        ): ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed","Product not found", "")
        );
    }


    // Insert new product
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insert(@RequestBody Product newProduct){
        List<Product> listProduct = repository.findByName(newProduct.getName().trim());
        if(listProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK","Insert product successfully", repository.save(newProduct))
            );
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("Failed","Product already exists", "")
            );
        }
    }


    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@PathVariable String id, @RequestBody Product newProduct){
        Product updatedProduct =  repository.findById(id)
                    .map(product1 -> {
            product1.setName(newProduct.getName());
            product1.setPrice(newProduct.getPrice());
            return repository.save(product1);
        }).orElseGet(() -> {
            newProduct.setName(newProduct.getName());
          return repository.save(newProduct);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK","Update product successfully", updatedProduct)
        );
    }


    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable String id){
        Optional<Product> exists = repository.findById(id);
       if(exists.isPresent()){
                repository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Delete Successfully", "")
                );
       }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                   new ResponseObject("Failed", "Can not find", "")
           );
       }

    }


}
