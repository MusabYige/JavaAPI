package com.example.api.service;

import com.example.api.repository.TransactionRepository;
import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Yeni bir kullanıcı oluşturur ve kaydeder.
     * @param user oluşturulacak kullanıcı nesnesi
     * @return kaydedilen kullanıcı nesnesi
     */

    @Transactional
    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    /**
     * Belirtilen ID'ye sahip kullanıcıyı döner.
     * @param id kullanıcı ID'si
     * @return bulunan kullanıcı nesnesi
     */

    @Transactional
    public User getUser(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Belirtilen ID'ye sahip kullanıcıyı günceller.
     * @param id güncellenecek kullanıcının ID'si
     * @param userDetails kullanıcının güncel bilgileri
     * @return güncellenen kullanıcı nesnesi
     */

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = getUser(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        userRepository.update(user);
        return user;
    }

    /**
     * Belirtilen ID'ye sahip kullanıcıyı siler.
     * @param id silinecek kullanıcının ID'si
     */
    @Transactional
    public void deleteUser(Long id) {
        // First, delete or handle the user's transactions
        transactionRepository.deleteByUserId(id);

        // Then, delete the user
        userRepository.delete(id);
    }
}
