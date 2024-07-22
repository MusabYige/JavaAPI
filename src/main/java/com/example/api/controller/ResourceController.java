package com.example.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Kontrolcü korunan kaynaklara erişim sağlar.
 * Bu kontrolcü, belirli API yolları için korunan kaynaklara erişimi yönetir.
 */
@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    /**
     * Korunan bir kaynağı alır ve döndürür.
     * Bu yöntem, korunan bir kaynağa erişim sağlamak için bir HTTP GET isteğine yanıt verir.
     *
     * @return Korunan kaynağın içeriğini içeren ResponseEntity nesnesi.
     */
    @GetMapping
    public ResponseEntity<?> getResource() {
        return ResponseEntity.ok(new HashMap<String, String>() {{
            put("data", "This is a protected resource");
        }});
    }
}