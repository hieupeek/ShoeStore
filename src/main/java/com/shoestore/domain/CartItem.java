package com.shoestore.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    // --- RELATIONS ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // Mấu chốt: Chọn đúng Size/Màu (ProductDetail) chứ không chọn Product chung
    // chung
    @ManyToOne(fetch = FetchType.EAGER) // Eager để hiện giỏ hàng cho nhanh
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;
}