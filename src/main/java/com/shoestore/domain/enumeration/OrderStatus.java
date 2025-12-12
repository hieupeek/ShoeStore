package com.shoestore.domain.enumeration;

public enum OrderStatus {
    PENDING, // Chờ xử lý (Mới đặt)
    CONFIRMED, // Đã xác nhận (Admin/Staff duyệt)
    SHIPPING, // Đang giao
    COMPLETED, // Giao thành công
    CANCELLED, // Hủy
    RETURNED // Trả hàng
}