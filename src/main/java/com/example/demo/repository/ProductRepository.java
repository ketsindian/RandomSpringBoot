package com.example.demo.repository;

import com.example.demo.data.FilesMarked;
import com.example.demo.data.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}