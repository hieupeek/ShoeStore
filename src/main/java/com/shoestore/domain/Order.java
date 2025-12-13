package com.shoestore.domain;

import com.shoestore.domain.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code; // Mã vận đơn (VD: ORD-170293)

    // Lưu snapshot giá tiền (tránh việc sản phẩm đổi giá sau này)
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING) // Lưu chữ "PENDING" vào DB cho dễ đọc
    private OrderStatus status;

    // Thông tin người nhận (Snapshot từ Address hoặc nhập mới)
    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "recipient_phone")
    private String recipientPhone;

    @Column(name = "recipient_address")
    private String recipientAddress;

    // Snapshot: Lưu lại số tiền được giảm CỤ THỂ tại thời điểm đặt hàng.
    // Lý do: Nếu sau này Voucher bị sửa từ giảm 50k xuống còn 10k,
    // thì đơn hàng cũ vẫn phải hiển thị là đã giảm 50k (không được đổi).
    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    private String note;

    // --- RELATIONS ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // Relation: Nối sang bảng Voucher để biết dùng mã nào (để thống kê)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
}