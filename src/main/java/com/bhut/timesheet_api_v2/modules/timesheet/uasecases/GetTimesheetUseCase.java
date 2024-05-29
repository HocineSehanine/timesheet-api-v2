package com.bhut.timesheet_api_v2.modules.timesheet.uasecases;

import com.bhut.timesheet_api_v2.modules.holiday.entities.HolidayEntity;
import com.bhut.timesheet_api_v2.modules.holiday.repository.HolidayRepository;
import com.bhut.timesheet_api_v2.modules.timesheet.entities.ReleaseEntity;
import com.bhut.timesheet_api_v2.modules.timesheet.repository.ReleasesRepository;
import com.bhut.timesheet_api_v2.modules.timesheet.responses.ReportDaysResponse;
import com.bhut.timesheet_api_v2.modules.timesheet.responses.ReportPeriodResponse;
import com.bhut.timesheet_api_v2.modules.timesheet.responses.TimesheetResponse;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetTimesheetUseCase {

    private final ReleasesRepository releasesRepository;
    private final HolidayRepository holidayRepository;

    GetTimesheetUseCase(final ReleasesRepository releasesRepository, final HolidayRepository holidayRepository) {
        this.releasesRepository = releasesRepository;
        this.holidayRepository = holidayRepository;
    }

    public TimesheetResponse execute(final String userId, final int year, final int month) {
        List<ReleaseEntity> releases = releasesRepository.findByUserIdAndYearAndMonth(userId, year, month);

        List<HolidayEntity> holidays = holidayRepository.findByYearAndMonth(year, month);

        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        List<ReportDaysResponse> reportDaysResponses = new ArrayList<>();

        int totalHoursMonth = 0;
        int expectedHoursPerDay = 8;

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);
            boolean businessDay = isBusinessDay(date, holidays);
            List<ReleaseEntity> dailyReleases = releases.stream()
                    .filter(release -> release.getDate().equals(date))
                    .toList();

            int totalHoursWorked = dailyReleases.stream()
                    .mapToInt(this::calculateTotalHoursWorked)
                    .sum();
            int totalRemuneratedHoliday = holidays.stream().anyMatch(holidayEntity -> holidayEntity.getDate().equals(date)) ? 8 : 0;

            String totalHoursWorkedFormatted = String.format("%02d:00", totalHoursWorked + totalRemuneratedHoliday);

            List<ReportPeriodResponse> periods = dailyReleases.stream()
                    .map(release -> new ReportPeriodResponse(release.getStartTime(), release.getEndTime(), release.getDescription()))
                    .toList();
            if (holidays.stream().anyMatch(holidayEntity -> holidayEntity.getDate().equals(date))) {
                periods = List.of(ReportPeriodResponse.builder().start("08:00").end("17:00").description("FERIADO").build());
            }

            ReportDaysResponse reportDay = new ReportDaysResponse();
            reportDay.setDate(date.toString());
            reportDay.setBusinessDay(businessDay);
            reportDay.setPeriod(periods);
            reportDay.setTotal(totalHoursWorkedFormatted);

            reportDaysResponses.add(reportDay);

            totalHoursMonth += (totalHoursWorked + totalRemuneratedHoliday);
        }

        int businessDaysCount = (int) reportDaysResponses.stream().filter(ReportDaysResponse::isBusinessDay).count();
        int expectedHoursMonth = businessDaysCount * expectedHoursPerDay;
        double balance = totalHoursMonth - expectedHoursMonth;

        TimesheetResponse response = new TimesheetResponse();
        response.setMonth(month);
        response.setYear(year);
        response.setTotal(String.format("%02d:00", totalHoursMonth));
        response.setBalance(balance);
        response.setDays(reportDaysResponses);
        response.setClosed(this.isClosed(year, Month.of(month)));

        return response;
    }

    private boolean isBusinessDay(LocalDate date, List<HolidayEntity> holidayDates) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return false;
        }
        return holidayDates.stream().noneMatch(holidayEntity -> holidayEntity.getDate().equals(date));
    }

    private int calculateTotalHoursWorked(ReleaseEntity release) {
        if (release.getStartTime() != null && release.getEndTime() != null) {
            LocalTime startTime = LocalTime.parse(release.getStartTime());
            LocalTime endTime = LocalTime.parse(release.getEndTime());
            return (int) Duration.between(startTime, endTime).toHours();
        }

        return 0;
    }

    private boolean isClosed(int year, Month month) {
        final var currentDate = LocalDate.now();
        final var givenDate = LocalDate.of(year, month, 1).withDayOfMonth(1);

        return givenDate.isBefore(currentDate.withDayOfMonth(1));
    }
}
