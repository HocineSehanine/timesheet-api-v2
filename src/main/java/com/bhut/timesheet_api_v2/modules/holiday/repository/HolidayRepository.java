package com.bhut.timesheet_api_v2.modules.holiday.repository;

import com.bhut.timesheet_api_v2.modules.holiday.entities.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Repository
public interface HolidayRepository  extends JpaRepository<HolidayEntity, String> {

    void deleteByYear(final int year);
    List<HolidayEntity> findByYearAndMonth(int year, int month);
    List<HolidayEntity> findByYear(final int year);
    HolidayEntity findByDate(final LocalDate date);
}
