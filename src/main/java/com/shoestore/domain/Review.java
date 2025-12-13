package com.shoestore.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rating; // Số sao: 1 đến 5

    @Column(columnDefinition = "TEXT")
    private String comment; // Nội dung đánh giá

    private Integer status; // 1: Hiện (Approved), 0: Ẩn (Spam/Chờ duyệt)

    // --- RELATIONS ---

    // Ai là người viết?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Review cho sản phẩm nào? (Để query hiển thị cho nhanh)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // Review từ đơn hàng nào? (Chứng minh đã mua thật - Verified Purchase)
    // OneToOne: Mỗi dòng chi tiết đơn hàng chỉ được review 1 lần thôi.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id")
    private OrderDetail orderDetail;
}