package com.shoestore.repository;

import com.shoestore.domain.ImportReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportReceiptRepository extends JpaRepository<ImportReceipt, Long> {
    // Có thể tìm theo mã phiếu nhập
    // Optional<ImportReceipt> findByCode(String code);
}