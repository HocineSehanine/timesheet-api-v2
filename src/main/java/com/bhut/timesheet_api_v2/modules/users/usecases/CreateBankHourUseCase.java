package com.bhut.timesheet_api_v2.modules.users.usecases;

import com.bhut.timesheet_api_v2.modules.users.entities.HoursBankEntity;
import com.bhut.timesheet_api_v2.modules.users.repository.HoursBankRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateBankHourUseCase {

    private final HoursBankRepository hoursBankRepository;

    public CreateBankHourUseCase(final HoursBankRepository hoursBankRepository) {
        this.hoursBankRepository = hoursBankRepository;
    }

    public void execute(final String userId, final HoursBankEntity hoursBankEntity) {

        hoursBankRepository.save(hoursBankEntity);
    }
}
