package com.shoestore.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_name")
    private String recipientName; // Tên người nhận (có thể khác tên chủ acc)

    @Column(name = "recipient_phone")
    private String recipientPhone;

    private String province; // Tỉnh/Thành
    private String district; // Quận/Huyện
    private String ward; // Phường/Xã

    @Column(name = "detail_address")
    private String detailAddress; // Số nhà, đường...

    @Column(name = "is_default")
    private Boolean isDefault = false; // Địa chỉ mặc định

    // --- RELATIONS ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}