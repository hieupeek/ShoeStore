package com.shoestore.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "vouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voucher extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // VD: SALE50, TET2025

    @Column(name = "discount_amount")
    private BigDecimal discountAmount; // Giảm tiền mặt (VD: 50.000)

    @Column(name = "discount_percent")
    private Integer discountPercent; // Giảm phần trăm (VD: 10%)

    @Column(name = "min_order_value")
    private BigDecimal minOrderValue; // Đơn tối thiểu được dùng

    @Column(name = "start_date")
    private Instant startDate; // Ngày bắt đầu

    @Column(name = "end_date")
    private Instant endDate; // Ngày hết hạn

    private Integer quantity; // Số lượng mã (VD: chỉ có 100 mã)

    private Integer status; // 1: Active, 0: Expired/Disabled
}