package com.bhut.timesheet_api_v2.modules.timesheet.repository;

import com.bhut.timesheet_api_v2.modules.timesheet.entities.ReleaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReleasesRepository extends JpaRepository<ReleaseEntity, String> {

    ReleaseEntity findByDateAndUserId(final LocalDate date, final String userId);
    List<ReleaseEntity> findByUserIdAndYearAndMonth(final String userId, final int year, final int month);
}
