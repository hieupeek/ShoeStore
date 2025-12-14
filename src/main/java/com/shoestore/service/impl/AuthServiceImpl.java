package com.shoestore.service.impl;

import com.shoestore.domain.Role;
import com.shoestore.domain.User;

import com.shoestore.repository.RoleRepository;
import com.shoestore.repository.UserRepository;

import com.shoestore.service.AuthService;

import com.shoestore.service.dto.LoginDTO;
import com.shoestore.service.dto.LoginResponseDTO;
import com.shoestore.service.dto.RegisterDTO;

import com.shoestore.service.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoestore.security.JwtTokenProvider; // Import class mới

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    // Dependencies được tiêm vào (Dependency Injection)
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper; // <--- Sử dụng Mapper
    private final JwtTokenProvider jwtTokenProvider; // <--- Sử dụng JwtTokenProvider
    private final com.shoestore.service.RefreshTokenService refreshTokenService;

    @Override
    public User registerUser(RegisterDTO registerDTO) {
        // 1. Validation Logic (Business Rules)
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại!");
            // Sau này ta sẽ thay bằng Custom Exception chuyên nghiệp hơn
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng!");
        }

        // 2. Mapping Logic (Đã tách ra Mapper -> Clean Code)
        User user = userMapper.toEntity(registerDTO);

        // 3. Security Logic
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // 4. Role Assignment Logic
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Hệ thống lỗi: Không tìm thấy Role USER"));
        user.setRole(userRole);

        // 5. Persistence Logic
        return userRepository.save(user);
    }

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        // 1. Tìm user trong DB
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Tài khoản không tồn tại!"));

        // 2. Kiểm tra mật khẩu (QUAN TRỌNG)
        // Tuyệt đối không dùng dấu == hoặc .equals() vì pass trong DB đã mã hóa
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Sai mật khẩu, vui lòng thử lại!");
        }

        // Tạo JWT Token thật
        String jwtToken = jwtTokenProvider.generateToken(user.getUsername());

        // Tạo Refresh Token
        com.shoestore.domain.RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        // 4. Trả về kết quả
        return LoginResponseDTO.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }

    @Override
    public com.shoestore.service.dto.TokenRefreshResponseDTO refreshToken(
            com.shoestore.service.dto.TokenRefreshRequestDTO request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(com.shoestore.domain.RefreshToken::getUser)
                .map(user -> {
                    String token = jwtTokenProvider.generateToken(user.getUsername());
                    return com.shoestore.service.dto.TokenRefreshResponseDTO.builder()
                            .accessToken(token)
                            .refreshToken(request.getRefreshToken())
                            .build();
                })
                .orElseThrow(() -> new IllegalArgumentException("Refresh token is not in database!"));
    }
}