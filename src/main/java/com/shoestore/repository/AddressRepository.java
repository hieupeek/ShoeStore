package com.shoestore.repository;

import com.shoestore.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // Tìm tất cả địa chỉ của 1 user
    List<Address> findByUserId(Long userId);
}