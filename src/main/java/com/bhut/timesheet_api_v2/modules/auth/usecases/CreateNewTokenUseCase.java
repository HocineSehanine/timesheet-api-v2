package com.bhut.timesheet_api_v2.modules.auth.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bhut.timesheet_api_v2.exceptions.RefreshTokenExpired;
import com.bhut.timesheet_api_v2.modules.auth.repository.RefreshTokenRepository;
import com.bhut.timesheet_api_v2.modules.auth.responses.CreateTokenResponse;
import com.bhut.timesheet_api_v2.modules.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class CreateNewTokenUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UsersRepository usersRepository;

    @Value("${jwtSecretKey.secret}")
    private String secret;

    CreateNewTokenUseCase(final RefreshTokenRepository refreshTokenRepository, final UsersRepository usersRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.usersRepository = usersRepository;
    }

    public CreateTokenResponse execute(final String refreshToken) {
        final var refreshTokenEntity = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("declared token does not exist"));
        if(refreshTokenEntity.getExpiresAt().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshTokenEntity);
            throw new RefreshTokenExpired();
        }
        final var user = usersRepository.findById(refreshTokenEntity.getUser().getId()).orElseThrow();
        var roles = List.of(user.getGroupId());

        final Algorithm algorithm = Algorithm.HMAC256(secret);
        final var tokenExpiresIn = Instant.now().plus(Duration.ofMinutes(720));
        final var accessToken = JWT.create().withIssuer(user.getId())
                .withExpiresAt(tokenExpiresIn)
                .withClaim("roles", roles)
                .withSubject(user.getId())
                .sign(algorithm);
        return CreateTokenResponse.builder().accessToken(accessToken).expiresIn(refreshTokenEntity.getExpiresAt().toEpochMilli()).refreshToken(refreshToken).tokenType("Bearer").build();
    }
}
