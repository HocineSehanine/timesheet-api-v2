package com.bhut.timesheet_api_v2.modules.auth.repository;

import com.bhut.timesheet_api_v2.modules.auth.entities.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String>{

    Optional<RefreshTokenEntity> findByRefreshToken(final String refreshToken);
}
