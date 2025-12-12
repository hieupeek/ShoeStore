package com.shoestore.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Số lượng tồn kho (QUAN TRỌNG VỚI NHÂN VIÊN)
    private Integer quantity;

    // Giá bán riêng cho size/màu này (nếu có)
    private BigDecimal price;

    // Mã SKU quản lý kho (VD: NK-AF1-39-RED)
    @Column(unique = true)
    private String sku;

    private Integer status;

    // --- RELATIONS ---

    // Thuộc về sản phẩm nào?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // Size gì?
    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    // Màu gì?
    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
}