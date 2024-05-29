package com.bhut.timesheet_api_v2.modules.auth.entities;

import com.bhut.timesheet_api_v2.modules.users.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity(name = "refresh_token")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "refresh_token", columnDefinition = "CHAR(36)")
    private String refreshToken;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @OneToOne
    @JoinColumn(name = "id_usuario", insertable=false, updatable=false)
    private UserEntity user;

    @Column(name = "id_usuario", columnDefinition = "CHAR(36)")
    private String userId;
}
