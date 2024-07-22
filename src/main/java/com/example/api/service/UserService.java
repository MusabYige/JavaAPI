package com.example.api.service;

import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Yeni bir kullanıcı oluşturur ve kaydeder.
     * @param user oluşturulacak kullanıcı nesnesi
     * @return kaydedilen kullanıcı nesnesi
     */
    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    /**
     * Belirtilen ID'ye sahip kullanıcıyı döner.
     * @param id kullanıcı ID'si
     * @return bulunan kullanıcı nesnesi
     */
    public User getUser(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Belirtilen ID'ye sahip kullanıcıyı günceller.
     * @param id güncellenecek kullanıcının ID'si
     * @param userDetails kullanıcının güncel bilgileri
     * @return güncellenen kullanıcı nesnesi
     */
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
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
}
