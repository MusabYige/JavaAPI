package com.example.api.config;

import com.example.api.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JWT tabanlı Spring Security uygulaması için güvenlik yapılandırma sınıfı.
 * Uygulamayı, istekleri doğrulamak ve yetkilendirmek için Spring Security ile yapılandırır.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * AuthenticationManager'ı, uygulamanın diğer bölümlerinde kullanılmak üzere bir Bean olarak sunar.
     * @param authenticationConfiguration Spring Security tarafından sağlanan kimlik doğrulama yapılandırması.
     * @return AuthenticationManager örneği.
     * @throws Exception AuthenticationManager oluşturulurken bir hata oluşursa.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * İsteklerin nasıl güvence altına alındığını belirtmek için HttpSecurity'yi yapılandırır.
     * Bu yöntem, jwtFilter'ı UsernamePasswordAuthenticationFilter'dan önce uygulayan güvenlik filtresi zincirini kurar.
     * Ayrıca, API güvenliği için gerekli olmayan CSRF korumasını devre dışı bırakarak oturum yönetimini durumsuz olarak yapılandırır.
     * @param http Yapılandırılacak HttpSecurity.
     * @return Yapılandırılmış SecurityFilterChain.
     * @throws Exception yapılandırma sırasında bir hata oluşursa.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Swagger dokümantasyonuna erişime izin verir
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Uygulamada şifreleri kodlamak için kullanılacak PasswordEncoder'ı tanımlar.
     * Bu uygulama, BCrypt hashing algoritmasını kullanır.
     * @return BCryptPasswordEncoder örneği.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}