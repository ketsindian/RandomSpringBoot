package com.example.demo.repository;

import com.example.demo.data.FilesMarked;
import com.example.demo.data.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilesMarkedRepository extends CrudRepository<FilesMarked, String> {

}
