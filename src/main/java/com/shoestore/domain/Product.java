package com.shoestore.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractAuditingEntity { // Kế thừa Audit (ngày tạo/sửa)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // Mã SP: NK-AF1

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer status; // 1: Active, 0: Inactive

    // --- RELATIONS (Mối quan hệ) ---

    // Nhiều Product thuộc 1 Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Nhiều Product thuộc 1 Brand
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    // 1 Product có NHIỀU Ảnh
    // mappedBy = "product": Ám chỉ biến "product" bên class ProductImage là chủ sở
    // hữu khóa ngoại
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> images = new ArrayList<>();

    // 1 Product có NHIỀU Biến thể (SKU)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductDetail> productDetails = new ArrayList<>();
}