# RandomSpringBoot

RandomSpringBoot is a Spring Boot application for a specific problem statement.

## Prerequisites to run the App.

This app needs following dependencies.
```bash
1. jdk 11
2. maven 3.6+
3. redis-server
```

## Steps to execute.
```bash
1. mvn clean package
2. java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Application Design
A. SyncJobs to sync the static and dynamic data in the directories.
```bash
1. TransactionSyncJobCommand syncs the newly added transaction files from folder location configured by key 
app.folderlocation.transaction . 
The recurring interval for job is configurable by key app.transactionsynccommand.intervalinminutes .
config keys are present in application.properties
2. ProductSyncJobCommand syncs the static product information from files present in another folder.
It has similar config keys like above job.
```

B. Data Storage. In memory data storage is implemented in two ways -
```bash
we can switch between these two implementations by configuring key app.data.implementation in application.properties.
1. In Memory static Data Structures handled by StoreTransactionService (active when app.data.implementation=storeService).
2. In Memory redis database handled by IMDBTransactionService (active when app.data.implementation=imdbService).

```

C. Rest APIs 
```bash
1 GET request http://localhost:8080/assignment/transaction/{transaction_id}
2 GET request http://localhost:8080/assignment/transactionSummaryByProducts/{last_n_days}
3 GET request http://localhost:8080/assignment/transactionSummaryByManufacturingCity/{last_n_da
ys}
```
