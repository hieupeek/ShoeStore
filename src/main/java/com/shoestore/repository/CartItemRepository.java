package com.shoestore.repository;

import com.shoestore.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Xóa tất cả item trong giỏ (khi thanh toán xong)
    void deleteAllByCartId(Long cartId);
}