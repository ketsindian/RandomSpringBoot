package com.example.demo.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

@Service
public class SyncJobExecutor {

    private final
    List<SyncJobCommandInterface> commands;

    public SyncJobExecutor(List<SyncJobCommandInterface> commands) {
        this.commands = commands;
    }

    @SuppressWarnings("rawtypes")
    public void execute() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        List<ScheduledFuture> futures = new ArrayList<>();
        for (SyncJobCommandInterface command:commands){
        futures.add(executor.scheduleAtFixedRate(command, 0, command.getRecurringInterval(), command.getIntervalUnit()));
        }
    }

}
