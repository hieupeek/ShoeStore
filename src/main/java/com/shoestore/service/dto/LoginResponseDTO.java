package com.shoestore.service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token; // Quan trọng nhất (sẽ chứa JWT sau này)
    private String username;
    private String email;
    private String role;
}