package com.bhut.timesheet_api_v2.modules.users.repository;


import com.bhut.timesheet_api_v2.modules.users.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface    UsersRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(final String email);
}
