package com.shoestore.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "import_receipts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportReceipt extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code; // Mã phiếu nhập (VD: PN001)

    @Column(name = "supplier_name")
    private String supplierName; // Nhập từ nhà cung cấp nào (Nike VN, Adidas US...)

    @Column(name = "total_money")
    private BigDecimal totalMoney; // Tổng tiền vốn bỏ ra nhập đợt này

    // --- RELATIONS ---

    // Ai là người nhập? (Thường là User có Role STAFF hoặc ADMIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private User employee;

    // Một phiếu nhập có nhiều dòng chi tiết
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<ImportReceiptDetail> receiptDetails = new ArrayList<>();
}