package com.shoestore.repository;

import com.shoestore.domain.RefreshToken;
import com.shoestore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // Tìm token trong DB xem có tồn tại không
    Optional<RefreshToken> findByToken(String token);

    // Xóa token của user (Dùng khi Logout hoặc xóa user)
    @Modifying
    int deleteByUser(User user);

    // Xóa token (Dùng khi Logout)
    @Modifying
    void deleteByToken(String token);
}