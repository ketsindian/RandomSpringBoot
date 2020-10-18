package com.example.demo.worker;

import com.example.demo.data.Transaction;
import com.example.demo.service.ITransactionService;
import com.example.demo.service.ServiceConfig;
import com.example.demo.store.Store;
import com.example.demo.utils.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class TransactionSyncJobCommand implements SyncJobCommandInterface {

    @Value("${app.transactionsynccommand.intervalinminutes}")
    private int intervalInMinutes;

    @Value("${app.data.implementation}")
    private String dataImplementation;

    final
    ITransactionService transactionService;

    public TransactionSyncJobCommand(ServiceConfig serviceConfig) {
        this.transactionService = serviceConfig.getTransactionService();
    }

    Logger logger = LoggerFactory.getLogger(TransactionSyncJobCommand.class);

    @Override
    public TimeUnit getIntervalUnit() {
        return TimeUnit.MINUTES;
    }

    @Override
    public long getRecurringInterval() {
        return this.intervalInMinutes;
    }

    @Override
    public void run() {
        try {
            logger.info("TransactionSyncJobCommand triggered");
            transactionService.syncTransactions();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }
}
