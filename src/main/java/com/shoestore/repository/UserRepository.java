package com.shoestore.repository;

import com.shoestore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring tự động hiểu câu này thành: SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);

    // Kiểm tra email đã tồn tại chưa (để validate khi đăng ký)
    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}