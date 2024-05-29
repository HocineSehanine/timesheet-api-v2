package com.bhut.timesheet_api_v2.modules.holiday.usecases;

import com.bhut.timesheet_api_v2.modules.holiday.entities.HolidayEntity;
import com.bhut.timesheet_api_v2.modules.holiday.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateHolidayUseCase {

    private final HolidayRepository holidayRepository;

    public CreateHolidayUseCase(final HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public void execute(final List<HolidayEntity> holidayEntities) {
        holidayRepository.saveAll(holidayEntities);
    }
}
