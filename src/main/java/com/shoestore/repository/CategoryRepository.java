package com.shoestore.repository;

import com.shoestore.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Hiện tại để trống cũng được, JpaRepository lo hết các hàm cơ bản rồi
}