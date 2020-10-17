package com.example.demo.worker;

import java.util.concurrent.TimeUnit;

public interface SyncJobCommandInterface extends Runnable {
    TimeUnit getIntervalUnit();

    long getRecurringInterval();

}
