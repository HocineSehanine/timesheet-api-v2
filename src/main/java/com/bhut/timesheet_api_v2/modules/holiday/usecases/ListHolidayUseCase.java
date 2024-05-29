package com.bhut.timesheet_api_v2.modules.holiday.usecases;

import com.bhut.timesheet_api_v2.modules.holiday.entities.HolidayEntity;
import com.bhut.timesheet_api_v2.modules.holiday.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ListHolidayUseCase {

    private final HolidayRepository holidayRepository;

    public ListHolidayUseCase(final HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public List<HolidayEntity> execute() {
        return holidayRepository.findByYear(LocalDate.now().getYear());
    }
}
