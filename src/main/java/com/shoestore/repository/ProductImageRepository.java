package com.shoestore.repository;

import com.shoestore.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    // Lấy list ảnh của 1 sp (dù Product đã có, nhưng cái này dùng để query nhanh
    // nếu cần)
    List<ProductImage> findByProductId(Long productId);
}