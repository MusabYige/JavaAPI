package com.example.api.repository;

import com.example.api.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repository sınıfı, harcamalarla ilgili veritabanı işlemlerini yönetir.
 * Harcamaları kaydetme, bulma, güncelleme ve silme işlevlerini sağlar.
 */
@Repository
public class ExpenseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Harcamaları sonuç kümesinden nesnelere dönüştürmek için kullanılan RowMapper.
     */
    private RowMapper<Expense> rowMapper = new RowMapper<Expense>() {
        @Override
        public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
            Expense expense = new Expense();
            expense.setId(rs.getLong("id"));
            expense.setName(rs.getString("name"));
            expense.setAmount(rs.getDouble("amount"));
            return expense;
        }
    };

    /**
     * Veritabanına yeni bir harcama kaydeder.
     *
     * @param expense Kaydedilecek harcama nesnesi.
     * @return Kaydedilen harcama nesnesi, ID ile birlikte.
     */
    public Expense save(Expense expense) {
        String sql = "INSERT INTO expenses (name, amount) VALUES (?, ?)";
        jdbcTemplate.update(sql, expense.getName(), expense.getAmount());
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        expense.setId(id);
        return expense;
    }

    /**
     * Veritabanındaki tüm harcamaları bulur.
     *
     * @return Harcama nesnelerinin listesi.
     */
    public List<Expense> findAll() {
        String sql = "SELECT * FROM expenses";
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * Belirli bir ID'ye sahip harcamayı bulur.
     *
     * @param id Bulunacak harcamanın ID'si.
     * @return Bulunan harcama nesnesi.
     */
    public Expense findById(Long id) {
        String sql = "SELECT * FROM expenses WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    /**
     * Belirli bir ID'ye sahip harcamayı günceller.
     *
     * @param id Güncellenecek harcamanın ID'si.
     * @param expense Güncellenecek harcama nesnesi.
     */
    public void update(Long id, Expense expense) {
        String sql = "UPDATE expenses SET name = ?, amount = ? WHERE id = ?";
        jdbcTemplate.update(sql, expense.getName(), expense.getAmount(), id);
    }

    /**
     * Belirli bir ID'ye sahip harcamayı siler.
     *
     * @param id Silinecek harcamanın ID'si.
     */
    public void deleteById(Long id) {
        String sql = "DELETE FROM expenses WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}