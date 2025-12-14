package com.shoestore.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // Dùng Builder pattern cho tiện khởi tạo
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token; // Chuỗi UUID ngẫu nhiên

    @Column(nullable = false)
    private Instant expiryDate; // Thời điểm hết hạn

    // Quan hệ: Một User có thể có nhiều Refresh Token (Login nhiều nơi)
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; // <-- Đổi 'User' thành tên class User/Account của bác nếu khác
}