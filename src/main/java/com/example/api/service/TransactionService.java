package com.example.api.service;

import com.example.api.model.AggregatedExpense;
import com.example.api.model.Transaction;
import com.example.api.repository.AggregatedExpenseRepository;
import com.example.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AggregatedExpenseRepository aggregatedExpenseRepository;

    /**
     * Yeni bir işlem oluşturur ve kaydeder.
     * @param transaction oluşturulacak işlem nesnesi
     * @return kaydedilen işlem nesnesi
     */

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        return transaction;
    }

    /**
     * Belirtilen kullanıcı ID'sine sahip tüm işlemleri döner.
     * @param userId kullanıcı ID'si
     * @return işlem listesi
     */

    @Transactional
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    /**
     * Tüm işlemleri döner.
     * @return işlem listesi
     */

    @Transactional
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * Belirtilen ID'ye sahip işlemi döner.
     * @param id işlem ID'si
     * @return bulunan işlem nesnesi
     */

    @Transactional
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    /**
     * Belirtilen ID'ye sahip işlemi günceller.
     * @param id güncellenecek işlemin ID'si
     * @param transactionDetails işlemin güncel bilgileri
     * @return güncellenen işlem nesnesi
     */

    @Transactional
    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id);
        transaction.setAmount(transactionDetails.getAmount());
        transaction.setDescription(transactionDetails.getDescription());
        transaction.setDate(transactionDetails.getDate());
        transactionRepository.update(transaction);
        return transaction;
    }

    /**
     * Belirtilen ID'ye sahip işlemi siler.
     * @param id silinecek işlemin ID'si
     */

    @Transactional
    public void deleteTransaction(Long id) {
        transactionRepository.delete(id);
    }

    /**
     * Belirtilen kullanıcı ID'sine sahip toplam harcamayı döner.
     * @param userId kullanıcı ID'si
     * @return toplam harcama miktarı
     */
    @Transactional
    public Double getTotalSpendingByUserId(Long userId) {
        return transactionRepository.getTotalSpendingByUserId(userId);
    }

    /**
     * Belirli bir periyotta kullanıcıların harcamalarını toplar ve kaydeder.
     * @param period toplama periyodu ("DAILY", "WEEKLY", "MONTHLY", "TEST")
     */

    @Transactional
    public void aggregateExpenses(String period) {
        List<Long> userIds = transactionRepository.findAllUserIds();
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.now();

        switch (period) {
            case "DAILY":
                startDateTime = LocalDateTime.now().minusDays(1);
                endDateTime = LocalDateTime.now();
                break;
            case "WEEKLY":
                startDateTime = LocalDateTime.now().minusWeeks(1);
                endDateTime = LocalDateTime.now();
                break;
            case "MONTHLY":
                startDateTime = LocalDateTime.now().minusMonths(1);
                endDateTime = LocalDateTime.now();
                break;
            case "TEST":
                startDateTime = LocalDateTime.now().minusMinutes(1);
                endDateTime = LocalDateTime.now();
                break;
        }

        for (Long userId : userIds) {
            Double total = transactionRepository.getTotalSpendingByUserIdAndPeriod(userId, startDateTime.toLocalDate(), endDateTime.toLocalDate());
            if (total != null && total > 0) {
                AggregatedExpense aggregatedExpense = new AggregatedExpense(userId, period, total);
                aggregatedExpense.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                aggregatedExpenseRepository.save(aggregatedExpense);
            }
        }
    }
}
