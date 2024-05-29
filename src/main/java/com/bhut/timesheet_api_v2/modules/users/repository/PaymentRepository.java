package com.bhut.timesheet_api_v2.modules.users.repository;

import com.bhut.timesheet_api_v2.modules.users.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
}
