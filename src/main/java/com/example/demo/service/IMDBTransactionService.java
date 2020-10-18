package com.example.demo.service;

import com.example.demo.data.*;
import com.example.demo.repository.FilesMarkedRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.store.Store;
import com.example.demo.utils.CSVReadHelper;
import com.example.demo.utils.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Qualifier("imdbService")
public class IMDBTransactionService extends HelperService implements ITransactionService{

    @Value("${app.folderlocation.product}")
    private String referenceFolderLocation;

    @Value("${app.folderlocation.transaction}")
    private String transactionFolderLocation;

    Logger logger = LoggerFactory.getLogger(IMDBTransactionService.class);

    final
    TransactionRepository transactionRepository;
    final
    FilesMarkedRepository filesMarkedRepository;
    final
    ProductRepository productRepository;


    public IMDBTransactionService(TransactionRepository transactionRepository, FilesMarkedRepository filesMarkedRepository, ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.filesMarkedRepository = filesMarkedRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Transaction> getLatestTransactionsFromFile() {
        File[] files = getUnreadTransactionFiles(this.transactionFolderLocation);
        if(files.length>0)
            logger.info("got new files to sync transactions "+files);
        List<Transaction> transactions = new ArrayList<>();
        Arrays.stream(files).forEachOrdered(file -> transactions.addAll(CSVReadHelper.readTransaction(file.getPath())));
        List<FilesMarked> filesMarkeds=new ArrayList<>();
        for (File file : files) {
            FilesMarked filesMarked=new FilesMarked();
            filesMarked.setFileName(file.getName());
            filesMarked.setMarked(true);
            filesMarkeds.add(filesMarked);
        }
        filesMarkedRepository.saveAll(filesMarkeds);
        if(transactions.size()>0)
            logger.info("got new transactions to sync "+transactions.size());
        return transactions;
    }

    @Override
    public Transaction getTransactionById(long transactionId) {
         Optional<Transaction> transactionOptional=transactionRepository.findById(transactionId);
         if(transactionOptional.isEmpty())
             throw new ResourceNotFoundException("transaction with id " + transactionId + " not found !");
        return transactionOptional.get();
    }

    @Override
    public List<SummaryByProduct> getSummaryByProduct(long numberOfDays) {
        List<Transaction> transactions= (List<Transaction>) transactionRepository.findAll();
        transactions=transactions.stream().filter(transaction -> transaction.getTransactionDatetime().
                after(Date.from(LocalDate.now().minusDays(numberOfDays).atStartOfDay(ZoneId.systemDefault()).toInstant()))).collect(Collectors.toList());
        HashMap<Long, Double> hmForAmountSUm = new HashMap<>();
        transactions.forEach(transaction -> {
            if (!hmForAmountSUm.containsKey(transaction.getProductId())) {
                hmForAmountSUm.put(transaction.getProductId(), (double) 0);
            }
            hmForAmountSUm.put(transaction.getProductId(), hmForAmountSUm.get(transaction.getProductId()) + transaction.getTransactionAmount());
        });
        return buildSummaryProductsFromHM(hmForAmountSUm);
    }

    @Override
    public List<SummaryByCity> getSummaryByCity(long numberOfDays) {
        List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
        transactions=transactions.stream().filter(transaction -> transaction.getTransactionDatetime().
                after(Date.from(LocalDate.now().minusDays(numberOfDays).atStartOfDay(ZoneId.systemDefault()).toInstant()))).collect(Collectors.toList());
        HashMap<String, Double> hmForAmountSUm = new HashMap<>();
        transactions.forEach(transaction -> {
            String cityName=productRepository.findById(transaction.getProductId()).get().getProductManufacturingCity();
            if (!hmForAmountSUm.containsKey(cityName)) {
                hmForAmountSUm.put(cityName, (double) 0);
            }
            hmForAmountSUm.put(cityName, hmForAmountSUm.get(cityName) + transaction.getTransactionAmount());
        });
        return buildSummaryCityFromHM(hmForAmountSUm);
    }

    @Override
    public void syncTransactions() {
        List<Transaction> transactionsToSync = this.getLatestTransactionsFromFile();
        logger.info("TransactionSyncJobCommand synced "+transactionsToSync.size()+" transactions");
        transactionRepository.saveAll(transactionsToSync);
    }

    @Override
    public void syncProducts() {
        List<Product> products=getStaticProductDataFromFile(this.referenceFolderLocation);
        productRepository.saveAll(products);
        logger.info("ProductSyncJobCommand synced products");
    }

    private List<SummaryByProduct> buildSummaryProductsFromHM(HashMap<Long, Double> hmForAmountSUm){
        List<SummaryByProduct> summaryByProducts = new ArrayList<>();
        hmForAmountSUm.forEach((aLong, aDouble) -> {
            SummaryByProduct summaryByProduct = new SummaryByProduct();
            summaryByProduct.setProductName(productRepository.findById(aLong).get().getProductName());
            summaryByProduct.setTotalAmount(aDouble);
            summaryByProducts.add(summaryByProduct);
        });
        return summaryByProducts;
    }

    private File[] getUnreadTransactionFiles(final String folderLocation) {
        File folder = new File(folderLocation);
        if (folder.isFile())
            throw new ResourceNotFoundException("configured folderlocation for product is not a directory " + folderLocation);
        if (Objects.isNull(folder.listFiles()) || folder.listFiles().length < 1)
            throw new ResourceNotFoundException("configured folderlocation for product does not contain any file " + folderLocation);
        return Arrays.stream(folder.listFiles()).filter(file -> !filesMarkedRepository.findById(file.getName()).isPresent()).toArray(File[]::new);
    }

}
