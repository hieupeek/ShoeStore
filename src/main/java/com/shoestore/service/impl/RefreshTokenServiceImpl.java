package com.shoestore.service.impl;

import com.shoestore.constant.Constant;

import com.shoestore.domain.RefreshToken;
import com.shoestore.domain.User;

import com.shoestore.repository.RefreshTokenRepository;
import com.shoestore.repository.UserRepository;

import com.shoestore.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.jwt.refresh-expiration}")
    private Long refreshExpirationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    // find by token (tim token trong db)
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    // create refresh token (tạo token)
    @Override
    @Transactional
    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(Constant.ERROR_USERNAME_NOT_FOUND));

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    // verify expiration (kiểm tra token có hết hạn không)
    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new IllegalArgumentException(Constant.ERROR_EXPIRED_JWT_TOKEN);
        }
        return token;
    }

    // delete refresh token by user id (logout trên tất cả các thiết bị)
    @Override
    @Transactional
    public int deleteRefreshTokenByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

    // logout 1 thiết bị (xóa token)
    @Override
    public void logout(String refreshTokenString) {
        refreshTokenRepository.findByToken(refreshTokenString)
                .ifPresent(refreshTokenRepository::delete);
    }

}
