package com.example.api.controller;

import com.example.api.model.Transaction;
import com.example.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontrolcü, işlemle ilgili işlemleri yönetir.
 * Kullanıcılar tarafından yapılan işlemleri oluşturma, alınma, güncellenme ve silinme işlemleri için uç noktalar sağlar.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Yeni bir işlem oluşturur.
     *
     * @param transaction Oluşturulacak işlem nesnesi.
     * @return Oluşturulan işlemi içeren ve HTTP durum kodu CREATED olan ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
    }

    /**
     * Belirli bir kullanıcıya ait tüm işlemleri alır.
     *
     * @param userId İşlemleri alınacak kullanıcının ID'si.
     * @return Kullanıcıya ait tüm işlemleri içeren ResponseEntity.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserId(userId));
    }

    /**
     * Tüm işlemleri alır.
     *
     * @return Sistemdeki tüm işlemleri içeren ResponseEntity.
     */
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    /**
     * ID'si verilen belirli bir işlemi alır.
     *
     * @param id Alınacak işlemin ID'si.
     * @return İstenen işlemi içeren ResponseEntity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    /**
     * Mevcut bir işlemi günceller.
     *
     * @param id Güncellenecek işlemin ID'si.
     * @param transactionDetails Güncellenmiş işlem detayları.
     * @return Güncellenmiş işlemi içeren ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transactionDetails) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, transactionDetails));
    }

    /**
     * ID'si verilen belirli bir işlemi siler.
     *
     * @param id Silinecek işlemin ID'si.
     * @return İçeriği olmayan ResponseEntity.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Belirli bir kullanıcının toplam harcamasını alır.
     *
     * @param userId Toplam harcama alınacak kullanıcının ID'si.
     * @return Kullanıcının toplam harcamasını içeren ResponseEntity.
     */
    @GetMapping("/total/{userId}")
    public ResponseEntity<Double> getTotalSpendingByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getTotalSpendingByUserId(userId));
    }
}