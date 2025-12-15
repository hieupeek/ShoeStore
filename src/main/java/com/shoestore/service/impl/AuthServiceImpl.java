package com.shoestore.service.impl;

import com.shoestore.constant.Constant;
import com.shoestore.domain.RefreshToken;
import com.shoestore.domain.Role;
import com.shoestore.domain.User;

import com.shoestore.repository.RoleRepository;
import com.shoestore.repository.UserRepository;

import com.shoestore.service.AuthService;

import com.shoestore.service.RefreshTokenService;

import com.shoestore.service.dto.LoginDTO;
import com.shoestore.service.dto.LoginResponseDTO;
import com.shoestore.service.dto.RegisterDTO;
import com.shoestore.service.dto.TokenRefreshRequestDTO;
import com.shoestore.service.dto.TokenRefreshResponseDTO;

import com.shoestore.service.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.shoestore.security.JwtTokenProvider;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    // Dependencies được tiêm vào (Dependency Injection)
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    // register user (tạo user)
    @Override
    public User registerUser(RegisterDTO registerDTO) {
        // 1. Validation Logic (Business Rules)
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new IllegalArgumentException(Constant.ERROR_USERNAME_EXISTS);
            // Sau này ta sẽ thay bằng Custom Exception chuyên nghiệp hơn
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException(Constant.ERROR_EMAIL_EXISTS);
        }

        // 2. Mapping Logic (Đã tách ra Mapper -> Clean Code)
        User user = userMapper.toEntity(registerDTO);

        // 3. Security Logic
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // 4. Role Assignment Logic
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException(Constant.ERROR_ROLE_NOT_FOUND));
        user.setRole(userRole);

        // 5. Persistence Logic
        return userRepository.save(user);
    }

    // login (đăng nhập)
    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        // 1. Tìm user trong DB
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException(Constant.ERROR_USERNAME_NOT_FOUND));

        // 2. Kiểm tra mật khẩu (QUAN TRỌNG)
        // Tuyệt đối không dùng dấu == hoặc .equals() vì pass trong DB đã mã hóa
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException(Constant.ERROR_WRONG_PASSWORD);
        }

        // Tạo JWT Token thật
        String jwtToken = jwtTokenProvider.generateAccessToken(user.getUsername());

        // Tạo Refresh Token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        // 4. Trả về kết quả
        return LoginResponseDTO.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }

    // refresh token (tạo lại token) khi access token cũ hết hạn
    @Override
    public TokenRefreshResponseDTO refreshToken(
            TokenRefreshRequestDTO request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtTokenProvider.generateAccessToken(user.getUsername());
                    return TokenRefreshResponseDTO.builder()
                            .accessToken(token)
                            .refreshToken(request.getRefreshToken())
                            .build();
                })
                .orElseThrow(() -> new IllegalArgumentException("Refresh token is not in database!"));
    }
}