package com.shoestore.repository;

import com.shoestore.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    // Logic tìm biến thể cụ thể có thể thêm sau

}