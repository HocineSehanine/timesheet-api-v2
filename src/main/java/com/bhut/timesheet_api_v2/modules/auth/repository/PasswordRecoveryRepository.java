package com.bhut.timesheet_api_v2.modules.auth.repository;

import com.bhut.timesheet_api_v2.modules.auth.entities.PasswordRecoveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecoveryEntity, String> {

    Optional<PasswordRecoveryEntity> findByCode(String code);
}
