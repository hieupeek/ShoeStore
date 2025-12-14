package com.shoestore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoestore.domain.enumeration.AuthenticationProvider;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // <--- DESIGN PATTERN: Builder
public class User extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore // Bảo mật: Không bao giờ trả về password khi convert sang JSON
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String avatar;

    private Integer status; // 1: Active, 0: Blocked

    // THÊM 2 FIELD NÀY:
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private AuthenticationProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    // --- RELATIONS ---

    // 1 User chỉ có 1 Role (Admin hoặc Staff hoặc User)
    // EAGER: Load User là load luôn Role (để Login cho nhanh)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    // 1 User có nhiều địa chỉ nhận hàng (Bảng Address bên dưới)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();
}