package com.example.demo.utils;

import com.example.demo.data.Product;
import com.example.demo.data.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVReadHelper {

    public static List<Transaction> readTransaction(final String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            getRecordFromLine(scanner.nextLine());
            while (scanner.hasNextLine()) {
                List<String> record = getRecordFromLine(scanner.nextLine());
                if (record.size() != 4) {
                    continue;
                }
                Transaction transaction = new Transaction();
                transaction.setTransactionId(Long.parseLong(record.get(0)));
                transaction.setProductId(Long.parseLong(record.get(1)));
                transaction.setTransactionAmount(Double.parseDouble(record.get(2)));
                transaction.setTransactionDatetime(Helper.extractDateFromCSV(record.get(3)));
                transactions.add(transaction);
            }
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static List<Product> readProduct(final String filePath) {
        List<Product> products = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            getRecordFromLine(scanner.nextLine());
            while (scanner.hasNextLine()) {
                List<String> record = getRecordFromLine(scanner.nextLine());
                if (record.size() != 3) {
                    continue;
                }
                Product product = new Product();
                product.setProductId(Long.parseLong(record.get(0)));
                product.setProductName(record.get(1));
                product.setProductManufacturingCity(record.get(2));
                products.add(product);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

}
