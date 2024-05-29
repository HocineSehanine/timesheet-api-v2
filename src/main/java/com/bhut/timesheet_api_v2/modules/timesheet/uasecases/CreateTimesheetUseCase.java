package com.bhut.timesheet_api_v2.modules.timesheet.uasecases;

import com.bhut.timesheet_api_v2.exceptions.BusinessRuleException;
import com.bhut.timesheet_api_v2.modules.holiday.repository.HolidayRepository;
import com.bhut.timesheet_api_v2.modules.timesheet.entities.ReleaseEntity;
import com.bhut.timesheet_api_v2.modules.timesheet.repository.ReleasesRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static com.bhut.timesheet_api_v2.modules.timesheet.mappers.TimesheetControllerMapper.MAPPER;
import static java.util.Objects.nonNull;

@Service
public class CreateTimesheetUseCase {

    private final ReleasesRepository releasesRepository;
    private final HolidayRepository holidayRepository;

    public CreateTimesheetUseCase(final ReleasesRepository releasesRepository, final HolidayRepository holidayRepository) {
        this.releasesRepository = releasesRepository;
        this.holidayRepository = holidayRepository;
    }

    public void execute(ReleaseEntity releaseEntity, final LocalDate date) {
        if (this.isClosed(date.getYear(), date.getMonth())) {
            throw new BusinessRuleException("The month/year is closed for updates.");
        }
        this.validatePeriods(releaseEntity.getStartTime(), releaseEntity.getEndTime());
        final var holidayEntity = holidayRepository.findByDate(date);
        releaseEntity.setHoliday(nonNull(holidayEntity) && date.equals(holidayEntity.getDate()));

        final var existingRelease = releasesRepository.findByDateAndUserId(date, releaseEntity.getUserId());
        if (nonNull(existingRelease)) {
            updateExistingRelease(existingRelease, releaseEntity, date.getMonth().getValue(), date.getYear());
            return;
        }
        if (nonNull(releaseEntity.getStartTime()) && nonNull(releaseEntity.getEndTime())) {
            releaseEntity.setTotal(calculateTotalTime(releaseEntity.getStartTime(), releaseEntity.getEndTime()));
        }
        releaseEntity.setUserId(releaseEntity.getUserId());
        releaseEntity.setMonth(date.getMonth().getValue());
        releaseEntity.setYear(date.getYear());
        releasesRepository.save(releaseEntity);
    }

    private void updateExistingRelease(final ReleaseEntity releaseEntityTobeUpdated, final ReleaseEntity releaseEntity, final int month, final int year) {
        MAPPER.updatedReleaseEntity(releaseEntityTobeUpdated, releaseEntity);
        if (nonNull(releaseEntity.getStartTime()) && nonNull(releaseEntity.getEndTime())) {
            releaseEntityTobeUpdated.setTotal(calculateTotalTime(releaseEntity.getStartTime(), releaseEntity.getEndTime()));
        }
        releaseEntityTobeUpdated.setMonth(month);
        releaseEntityTobeUpdated.setYear(year);
        releasesRepository.save(releaseEntityTobeUpdated);
    }

    private String calculateTotalTime(final String startTime, final String endTime) {
        var totalDuration = Duration.ZERO;
        final var start = LocalTime.parse(startTime);
        final var end = LocalTime.parse(endTime);
        totalDuration = totalDuration.plus(Duration.between(start, end));
        long hours = totalDuration.toHours();
        long minutes = totalDuration.toMinutes() % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    private void validatePeriods(final String startTime, final String endTime) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);

        if (start.isAfter(end)) {
            throw new BusinessRuleException("Start time cannot be after end time.");
        }

        if (start.isBefore(LocalTime.of(0, 0)) || end.isAfter(LocalTime.of(23, 59))) {
            throw new BusinessRuleException("Time period is out of bounds.");
        }
    }

    private boolean isClosed(int year, Month month) {
        final var currentDate = LocalDate.now();
        final var givenDate = LocalDate.of(year, month, 1).withDayOfMonth(1);

        return givenDate.isBefore(currentDate.withDayOfMonth(1));
    }
}
