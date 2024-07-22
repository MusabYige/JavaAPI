package com.example.api.repository;

import com.example.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Yeni bir kullanıcıyı veritabanına kaydeder.
     * @param user kaydedilecek kullanıcı
     * @return etkilenen satır sayısı
     */
    public int save(User user) {
        return jdbcTemplate.update("INSERT INTO users (name, email, password) VALUES (?, ?, ?)",
                user.getName(), user.getEmail(), user.getPassword());
    }

    /**
     * Belirli bir ID'ye sahip kullanıcıyı bulur.
     * @param id kullanıcının ID'si
     * @return kullanıcı nesnesi
     */
    public User findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
                new BeanPropertyRowMapper<>(User.class), id);
    }

    /**
     * Belirli bir e-posta adresine sahip kullanıcıyı bulur.
     * @param email kullanıcının e-posta adresi
     * @return kullanıcı nesnesi
     */
    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?",
                new BeanPropertyRowMapper<>(User.class), email);
    }

    /**
     * Mevcut bir kullanıcıyı günceller.
     * @param user güncellenecek kullanıcı
     * @return etkilenen satır sayısı
     */
    public int update(User user) {
        return jdbcTemplate.update("UPDATE users SET name = ?, email = ? WHERE id = ?",
                user.getName(), user.getEmail(), user.getId());
    }

    /**
     * Belirli bir ID'ye sahip kullanıcıyı siler.
     * @param id silinecek kullanıcının ID'si
     * @return etkilenen satır sayısı
     */
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }
}
