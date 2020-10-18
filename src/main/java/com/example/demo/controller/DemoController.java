package com.example.demo.controller;

import com.example.demo.data.SummaryByCity;
import com.example.demo.data.SummaryByProduct;
import com.example.demo.data.Transaction;
import com.example.demo.service.ITransactionService;
import com.example.demo.service.ServiceConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/assignment")
public class DemoController {

    private final ITransactionService transactionService;

    public DemoController(ServiceConfig serviceConfig) {
        this.transactionService = serviceConfig.getTransactionService();
    }

    @GetMapping("/transaction/{id}")
    public Transaction getTransaction(@PathVariable Integer id) {
        return this.transactionService.getTransactionById(id);
    }

    @GetMapping("/transactionSummaryByProducts/{last_n_days}")
    public List<SummaryByProduct> getTransactionSummaryByProducts(@PathVariable Long last_n_days) {
        return this.transactionService.getSummaryByProduct(last_n_days);
    }

    @GetMapping("/transactionSummaryByCities/{last_n_days}")
    public List<SummaryByCity> getTransactionSummaryByCities(@PathVariable Long last_n_days) {
        return this.transactionService.getSummaryByCity(last_n_days);
    }

}
