package com.example.demo.controller;

import com.example.demo.data.Transaction;
import com.example.demo.service.ITransactionService;
import com.example.demo.utils.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assignment")
public class DemoController {

    private final ITransactionService transactionService;

    public DemoController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transaction/{id}")
    public Transaction getTransaction(@PathVariable Integer id) throws TransactionException {
            return this.transactionService.getTransactionById(id);
    }

}
