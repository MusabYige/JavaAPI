package com.example.api.controller;

import com.example.api.model.Expense;
import com.example.api.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Harcamalarla ilgili işlemleri yöneten kontrolcü.
 * Harcamaları oluşturma, alınma, güncellenme ve silinme işlemleri için uç noktalar sağlar.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    /**
     * Yeni bir harcama oluşturur.
     *
     * @param expense Oluşturulacak harcama nesnesi.
     * @return Oluşturulan harcamayı içeren ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.createExpense(expense));
    }

    /**
     * Tüm harcamaları alır.
     *
     * @return Tüm harcamaların listesini içeren ResponseEntity.
     */
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    /**
     * ID'si verilen belirli bir harcamayı alır.
     *
     * @param id Alınacak harcamanın ID'si.
     * @return İstenen harcamayı içeren ResponseEntity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    /**
     * Mevcut bir harcamayı günceller.
     *
     * @param id Güncellenecek harcamanın ID'si.
     * @param expenseDetails Güncellenmiş harcama detayları.
     * @return Güncellenmiş harcamayı içeren ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expenseDetails) {
        expenseService.updateExpense(id, expenseDetails);
        return ResponseEntity.ok(expenseDetails);
    }

    /**
     * ID'si verilen belirli bir harcamayı siler.
     *
     * @param id Silinecek harcamanın ID'si.
     * @return İçeriği olmayan ResponseEntity.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}