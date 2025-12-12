package com.shoestore.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "import_receipt_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportReceiptDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity; // Số lượng nhập thêm (Sẽ cộng vào ProductDetail)

    @Column(name = "import_price")
    private BigDecimal importPrice; // Giá vốn nhập vào (Khác giá bán nhé)

    // --- RELATIONS ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private ImportReceipt receipt;

    // Nhập cho biến thể nào? (Size 40 hay 41, Đỏ hay Đen)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;
}