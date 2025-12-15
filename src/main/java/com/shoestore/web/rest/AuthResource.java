package com.shoestore.web.rest;

import com.shoestore.constant.Constant;

import com.shoestore.service.AuthService;

import com.shoestore.service.dto.LoginDTO;
import com.shoestore.service.dto.LoginResponseDTO;
import com.shoestore.service.dto.RegisterDTO;
import com.shoestore.service.dto.TokenRefreshRequestDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthResource {

    // Áp dụng Dependency Inversion: Chỉ gọi Interface, không gọi Class cụ thể
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            authService.registerUser(registerDTO);
            return ResponseEntity.ok(Constant.SUCCESS_REGISTER);
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

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @Valid @RequestBody TokenRefreshRequestDTO request) {
        try {
            return ResponseEntity.ok(authService.refreshToken(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}