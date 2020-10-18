package com.example.demo.service;

import com.example.demo.repository.FilesMarkedRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Value("${app.data.implementation}")
    private String serviceImplName;

    final
    TransactionRepository transactionRepository;
    final
    FilesMarkedRepository filesMarkedRepository;
    final
    ProductRepository productRepository;

    public ServiceConfig(TransactionRepository transactionRepository, FilesMarkedRepository filesMarkedRepository, ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.filesMarkedRepository = filesMarkedRepository;
        this.productRepository = productRepository;
    }

    Logger logger = LoggerFactory.getLogger(ServiceConfig.class);

    @Bean
    public ITransactionService getTransactionService() {
        if(serviceImplName.equals("imdbService")){
            logger.info("picked imdbServiceImplementation");
            return new IMDBTransactionService(this.transactionRepository,this.filesMarkedRepository,this.productRepository);
        }
        else{
            logger.info("picked storeServiceImplementation");
            return new StoreTransactionService();
        }
    }

}
