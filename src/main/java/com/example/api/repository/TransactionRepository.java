package com.example.api.repository;

import com.example.api.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TransactionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Yeni bir işlemi veritabanına kaydeder.
     * @param transaction kaydedilecek işlem
     * @return etkilenen satır sayısı
     */
    public int save(Transaction transaction) {
        return jdbcTemplate.update("INSERT INTO transactions (amount, description, date, user_id) VALUES (?, ?, ?, ?)",
                transaction.getAmount(), transaction.getDescription(), transaction.getDate(), transaction.getUserId());
    }

    /**
     * Belirli bir ID'ye sahip işlemi bulur.
     * @param id işlemin ID'si
     * @return işlem nesnesi
     */
    public Transaction findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM transactions WHERE id = ?",
                new BeanPropertyRowMapper<>(Transaction.class), id);
    }

    /**
     * Belirli bir kullanıcı ID'sine sahip tüm işlemleri bulur.
     * @param userId kullanıcı ID'si
     * @return belirtilen kullanıcıya ait işlemlerin listesi
     */
    public List<Transaction> findByUserId(Long userId) {
        return jdbcTemplate.query("SELECT * FROM transactions WHERE user_id = ?",
                new BeanPropertyRowMapper<>(Transaction.class), userId);
    }

    /**
     * Veritabanındaki tüm işlemleri döner.
     * @return tüm işlemlerin listesi
     */
    public List<Transaction> findAll() {
        return jdbcTemplate.query("SELECT * FROM transactions",
                new BeanPropertyRowMapper<>(Transaction.class));
    }

    /**
     * Mevcut bir işlemi günceller.
     * @param transaction güncellenecek işlem
     * @return etkilenen satır sayısı
     */
    public int update(Transaction transaction) {
        return jdbcTemplate.update("UPDATE transactions SET amount = ?, description = ?, date = ? WHERE id = ?",
                transaction.getAmount(), transaction.getDescription(), transaction.getDate(), transaction.getId());
    }

    /**
     * Belirli bir ID'ye sahip işlemi siler.
     * @param id silinecek işlemin ID'si
     * @return etkilenen satır sayısı
     */
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM transactions WHERE id = ?", id);
    }

    /**
     * Belirli bir kullanıcı ID'sine sahip toplam harcamaları hesaplar.
     * @param userId kullanıcı ID'si
     * @return toplam harcama miktarı
     */
    public Double getTotalSpendingByUserId(Long userId) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, Double.class);
    }

    /**
     * Tüm benzersiz kullanıcı ID'lerini döner.
     * @return tüm benzersiz kullanıcı ID'lerinin listesi
     */
    public List<Long> findAllUserIds() {
        String sql = "SELECT DISTINCT user_id FROM transactions";
        return jdbcTemplate.queryForList(sql, Long.class);
    }

    /**
     * Belirli bir kullanıcı için belirli bir dönemdeki toplam harcamaları hesaplar.
     * @param userId kullanıcı ID'si
     * @param startDate dönemin başlangıç tarihi
     * @param endDate dönemin bitiş tarihi
     * @return belirli dönemdeki toplam harcama miktarı
     */
    public Double getTotalSpendingByUserIdAndPeriod(Long userId, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND date >= ? AND date <= ?";
        Double total = jdbcTemplate.queryForObject(sql, new Object[]{userId, java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate)}, Double.class);
        return (total != null) ? total : 0.0;
    }
}
