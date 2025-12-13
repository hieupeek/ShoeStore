package com.shoestore.web.rest;

import com.shoestore.service.AuthService;

import com.shoestore.service.dto.LoginDTO;
import com.shoestore.service.dto.LoginResponseDTO;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            // Gọi xuống Service
            LoginResponseDTO response = authService.login(loginDTO);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Nếu sai pass hoặc không tìm thấy user -> Trả về lỗi 401 Unauthorized
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}