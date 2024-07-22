package com.example.api.service;

import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Belirtilen kullanıcı adı (e-posta) ile kullanıcıyı yükler.
     * @param username kullanıcının e-posta adresi
     * @return UserDetails nesnesi
     * @throws UsernameNotFoundException kullanıcı bulunamazsa fırlatılır
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

    /**
     * Yeni bir kullanıcıyı kaydeder.
     * @param user kaydedilecek kullanıcı
     */
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
