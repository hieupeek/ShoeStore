package com.shoestore.service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token; // Access Token
    private String refreshToken; // Refresh Token
    private String username;
    private String email;
    private String role;
}