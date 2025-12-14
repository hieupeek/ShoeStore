package com.shoestore.service.mapper;

import com.shoestore.domain.enumeration.AuthenticationProvider;
import com.shoestore.domain.User;
import com.shoestore.service.dto.RegisterDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Pattern: Mapper/Converter
    // Nhiệm vụ duy nhất: Chuyển cái hộp RegisterDTO thành Entity User
    public User toEntity(RegisterDTO dto) {
        if (dto == null) {
            return null;
        }

        // Sử dụng Builder Pattern ở đây cho code gọn gàng
        return User.builder()
                .username(dto.getUsername())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .status(1) // Mặc định Active
                // THÊM DÒNG NÀY: Mặc định là tài khoản thường
                .provider(AuthenticationProvider.LOCAL)
                .build();
    }
}