package com.example.demo;

import com.example.demo.data.Product;
import com.example.demo.data.Transaction;
import com.example.demo.run.DemoApplication;
import com.example.demo.utils.CSVReadHelper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void runCSVRead() {
        List<Transaction> transactions = CSVReadHelper.readTransaction("D:\\sandbox\\demo\\src\\main\\resources\\static\\transaction.csv");
        System.out.println("read transactions - " + transactions);
        List<Product> products = CSVReadHelper.readProduct("D:\\sandbox\\demo\\src\\main\\resources\\static\\product.csv");
        System.out.println("read products - " + products);
    }

}
