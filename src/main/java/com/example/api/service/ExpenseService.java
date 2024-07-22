package com.example.api.service;

import com.example.api.model.Expense;
import com.example.api.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    /**
     * Yeni bir masraf oluşturur ve kaydeder.
     * @param expense oluşturulacak masraf nesnesi
     * @return kaydedilen masraf nesnesi
     */
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    /**
     * Tüm masrafları döner.
     * @return masraf listesi
     */
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    /**
     * Belirtilen ID'ye sahip masrafı döner.
     * @param id masraf ID'si
     * @return bulunan masraf nesnesi
     */
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    /**
     * Belirtilen ID'ye sahip masrafı günceller.
     * @param id güncellenecek masrafın ID'si
     * @param expenseDetails masrafın güncel bilgileri
     */
    public void updateExpense(Long id, Expense expenseDetails) {
        expenseRepository.update(id, expenseDetails);
    }

    /**
     * Belirtilen ID'ye sahip masrafı siler.
     * @param id silinecek masrafın ID'si
     */
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
