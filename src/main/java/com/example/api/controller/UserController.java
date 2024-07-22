package com.example.api.controller;

import com.example.api.model.User;
import com.example.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Kullanıcı işlemlerini yöneten kontrolcü sınıfı.
 * Kullanıcı oluşturma, alınma, güncellenme ve silinme işlemleri için REST API uç noktalarını sağlar.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Yeni bir kullanıcı oluşturur.
     *
     * @param user Oluşturulacak kullanıcı nesnesi.
     * @return Oluşturulan kullanıcıyı ve HTTP durum kodu CREATED içeren ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    /**
     * Belirtilen ID'ye sahip kullanıcıyı alır.
     *
     * @param id Alınacak kullanıcının ID'si.
     * @return İstenen kullanıcıyı içeren ResponseEntity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    /**
     * Belirtilen ID'ye sahip kullanıcıyı günceller.
     *
     * @param id Güncellenecek kullanıcının ID'si.
     * @param userDetails Güncellenmiş kullanıcı detayları.
     * @return Güncellenmiş kullanıcıyı içeren ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

    /**
     * Belirtilen ID'ye sahip kullanıcıyı siler.
     *
     * @param id Silinecek kullanıcının ID'si.
     * @return İçeriği olmayan ResponseEntity.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}