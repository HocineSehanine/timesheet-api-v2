package com.bhut.timesheet_api_v2.modules.auth.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bhut.timesheet_api_v2.exceptions.UnauthorizedException;
import com.bhut.timesheet_api_v2.modules.auth.entities.RefreshTokenEntity;
import com.bhut.timesheet_api_v2.modules.auth.repository.RefreshTokenRepository;
import com.bhut.timesheet_api_v2.modules.auth.requests.CreateTokenRequest;
import com.bhut.timesheet_api_v2.modules.auth.responses.CreateTokenResponse;
import com.bhut.timesheet_api_v2.modules.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CreateTokenUseCase {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwtSecretKey.secret}")
    private String secret;

    public CreateTokenUseCase(final UsersRepository usersRepository, final PasswordEncoder passwordEncoder, final RefreshTokenRepository refreshTokenRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public CreateTokenResponse execute(final CreateTokenRequest createTokenRequest) {
        var user = usersRepository.findByEmail(createTokenRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Campo email ou senha esta incorreto"));

        final var passwordMatches = passwordEncoder.matches(createTokenRequest.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new UnauthorizedException();
        }

        var roles = List.of(user.getGroupId());

        final Algorithm algorithm = Algorithm.HMAC256(secret);
        final var tokenExpiresIn = Instant.now().plus(Duration.ofMinutes(240));
        final var token = JWT.create().withIssuer(user.getId())
                .withExpiresAt(tokenExpiresIn)
                .withClaim("roles", roles)
                .withSubject(user.getId())
                .sign(algorithm);
        final var refreshTokenExpiresIn = Instant.now().plus(Duration.ofMinutes(720));
        final var refreshTokenEntity = RefreshTokenEntity.builder().userId(user.getId()).refreshToken(UUID.randomUUID().toString()).expiresAt(refreshTokenExpiresIn).build();
        refreshTokenRepository.save(refreshTokenEntity);

        return CreateTokenResponse.builder().accessToken(token).expiresIn(refreshTokenExpiresIn.toEpochMilli()).refreshToken(refreshTokenEntity.getRefreshToken()).tokenType("Bearer").build();
    }
}
