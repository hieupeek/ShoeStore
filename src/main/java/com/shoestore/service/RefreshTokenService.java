package com.shoestore.service;

import com.shoestore.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(String username);

    RefreshToken verifyExpiration(RefreshToken token);

    int deleteRefreshTokenByUserId(Long userId);

    void logout(String refreshTokenString);
}
