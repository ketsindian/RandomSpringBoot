package com.example.demo.run;

import com.example.demo.worker.SyncJobExecutor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@ComponentScan(basePackages = {"com.example.*"})
@SpringBootApplication
@EnableRedisRepositories("com.example.*")
@EnableCaching
public class DemoApplication implements ApplicationRunner {

    final
    SyncJobExecutor syncJobExecutor;

    private final ApplicationContext applicationContext;

    public DemoApplication(ApplicationContext applicationContext, SyncJobExecutor syncJobExecutor) {
        this.applicationContext = applicationContext;
        this.syncJobExecutor = syncJobExecutor;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        syncJobExecutor.execute();
    }
}
