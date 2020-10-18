package com.example.demo.worker;

import com.example.demo.data.Transaction;
import com.example.demo.service.ITransactionService;
import com.example.demo.store.Store;
import com.example.demo.utils.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class TransactionSyncJobCommand implements SyncJobCommandInterface {

    final
    ITransactionService transactionService;

    public TransactionSyncJobCommand(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    Logger logger = LoggerFactory.getLogger(TransactionSyncJobCommand.class);

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
            logger.info("TransactionSyncJobCommand triggered");
            List<Transaction> transactionsToSync = transactionService.getLatestTransactionsFromFile();
            Store.addTransactions(transactionsToSync);
            logger.info("TransactionSyncJobCommand synced "+transactionsToSync.size()+" transactions");
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }
}
