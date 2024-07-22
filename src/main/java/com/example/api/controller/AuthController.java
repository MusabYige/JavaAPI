package com.example.api.controller;

import com.example.api.model.User;
import com.example.api.service.CustomUserDetailsService;
import com.example.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Giriş ve kayıt gibi kimlik doğrulama isteklerini yöneten kontrolcü.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Bir kullanıcıyı doğrular ve JWT token döndürür.
     *
     * @param loginRequest Kullanıcının e-posta ve şifresini içeren giriş isteği.
     * @return Eğer kimlik doğrulama başarılı ise JWT token içeren bir ResponseEntity döndürür.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(loginRequest.getEmail());

        return ResponseEntity.ok(jwt);
    }

    /**
     * Sistemde yeni bir kullanıcı kaydeder.
     *
     * @param signUpRequest Kullanıcı detaylarını içeren kayıt olma isteği.
     * @return Kayıt başarılı ise başarı mesajı içeren bir ResponseEntity döndürür.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User signUpRequest) {
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userDetailsService.saveUser(signUpRequest);
        return ResponseEntity.ok("Kullanıcı başarıyla kaydedildi");
    }
}