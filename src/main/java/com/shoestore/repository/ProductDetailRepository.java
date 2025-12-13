package com.shoestore.repository;

import com.shoestore.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    // Logic tìm biến thể cụ thể có thể thêm sau
}