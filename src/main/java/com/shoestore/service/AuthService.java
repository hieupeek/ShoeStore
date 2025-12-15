package com.shoestore.service;

import com.shoestore.domain.User;

import com.shoestore.service.dto.LoginDTO;
import com.shoestore.service.dto.LoginResponseDTO;
import com.shoestore.service.dto.RegisterDTO;
import com.shoestore.service.dto.TokenRefreshRequestDTO;
import com.shoestore.service.dto.TokenRefreshResponseDTO;

public interface AuthService {
    User registerUser(RegisterDTO registerDTO);

    // Thêm hàm này
    LoginResponseDTO login(LoginDTO loginDTO);

    // Refresh Token
    TokenRefreshResponseDTO refreshToken(TokenRefreshRequestDTO request);
}