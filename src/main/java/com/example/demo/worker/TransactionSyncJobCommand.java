package com.example.demo.worker;

import com.example.demo.service.ITransactionService;
import com.example.demo.store.Store;
import com.example.demo.utils.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TransactionSyncJobCommand implements SyncJobCommandInterface {

    final
    ITransactionService transactionService;

    public TransactionSyncJobCommand(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public TimeUnit getIntervalUnit() {
        return TimeUnit.MINUTES;
    }

    @Override
    public long getRecurringInterval() {
        return 5;
    }

    @Override
    public void run() {
        try {
            Store.addTransactions(transactionService.getLatestTransactions());
            System.out.println("RUNNING TRANSACTION SYNC");
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }
}
