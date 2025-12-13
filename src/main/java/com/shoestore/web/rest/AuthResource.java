package com.shoestore.web.rest;

import com.shoestore.service.AuthService;
import com.shoestore.service.dto.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    // Áp dụng Dependency Inversion: Chỉ gọi Interface, không gọi Class cụ thể
    private final AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            authService.registerUser(registerDTO);
            return ResponseEntity.ok("Đăng ký thành công!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Xử lý lỗi tập trung
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}