package com.shoestore.service.impl;

import com.shoestore.domain.Role;
import com.shoestore.domain.User;
import com.shoestore.repository.RoleRepository;
import com.shoestore.repository.UserRepository;
import com.shoestore.service.AuthService;
import com.shoestore.service.dto.RegisterDTO;
import com.shoestore.service.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    // Dependencies được tiêm vào (Dependency Injection)
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper; // <--- Sử dụng Mapper

    public AuthServiceImpl(UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

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
}