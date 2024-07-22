package com.example.api.repository;

import com.example.api.model.AggregatedExpense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Veritabanında toplu harcamaları yönetmek için repository.
 * Toplu harcamaları kaydetme, kullanıcı ID'sine göre bulma ve tüm toplu harcamaları bulma metodlarını sağlar.
 */
@Repository
public class AggregatedExpenseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Veritabanında bir toplu harcama kaydını kaydeder.
     *
     * @param aggregatedExpense Kaydedilecek toplu harcama.
     * @return Etkilenen satır sayısı.
     */
    public int save(AggregatedExpense aggregatedExpense) {
        return jdbcTemplate.update("INSERT INTO aggregated_expenses (user_id, period, total_amount) VALUES (?, ?, ?)",
                aggregatedExpense.getUserId(), aggregatedExpense.getPeriod(), aggregatedExpense.getTotalAmount());
    }

    /**
     * Kullanıcı ID'sine göre toplu harcamaları bulur.
     *
     * @param userId Toplu harcamaları bulunacak kullanıcının ID'si.
     * @return Belirtilen kullanıcı için toplu harcamaların listesi.
     */
    public List<AggregatedExpense> findByUserId(Long userId) {
        return jdbcTemplate.query("SELECT * FROM aggregated_expenses WHERE user_id = ?",
                new BeanPropertyRowMapper<>(AggregatedExpense.class), userId);
    }

    /**
     * Veritabanındaki tüm toplu harcamaları bulur.
     *
     * @return Tüm toplu harcamaların listesi.
     */
    public List<AggregatedExpense> findAll() {
        return jdbcTemplate.query("SELECT * FROM aggregated_expenses",
                new BeanPropertyRowMapper<>(AggregatedExpense.class));
    }
}