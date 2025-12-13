package com.shoestore.repository;

import com.shoestore.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Tìm theo Category (VD: Lấy tất cả giày Sneaker)
    List<Product> findByCategoryId(Long categoryId);

    // Tìm theo Brand (VD: Lấy tất cả giày Nike)
    List<Product> findByBrandId(Long brandId);

    // Tìm kiếm theo tên (VD: tìm chữ "Air" -> ra Nike Air Force)
    // Containing = Tìm gần đúng (LIKE %name%)
    List<Product> findByNameContainingIgnoreCase(String name);
}