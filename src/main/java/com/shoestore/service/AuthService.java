package com.shoestore.service; // Nó nằm trực tiếp trong service

import com.shoestore.domain.User;
import com.shoestore.service.dto.LoginDTO;
import com.shoestore.service.dto.LoginResponseDTO;
import com.shoestore.service.dto.RegisterDTO;

public interface AuthService {
    User registerUser(RegisterDTO registerDTO);

    // Thêm hàm này
    LoginResponseDTO login(LoginDTO loginDTO);
}