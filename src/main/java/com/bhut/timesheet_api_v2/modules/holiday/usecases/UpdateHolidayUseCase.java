package com.bhut.timesheet_api_v2.modules.holiday.usecases;

import com.bhut.timesheet_api_v2.modules.holiday.entities.HolidayEntity;
import com.bhut.timesheet_api_v2.modules.holiday.repository.HolidayRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UpdateHolidayUseCase {

    private final HolidayRepository holidayRepository;

    public UpdateHolidayUseCase(final HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    @Transactional
    public void execute(List<HolidayEntity> holidayEntities) {
        if (holidayEntities.isEmpty()) {
            return;
        }

        final var year = holidayEntities.getFirst().getYear();
        holidayRepository.deleteByYear(year);

        holidayRepository.saveAll(holidayEntities);
    }
}
