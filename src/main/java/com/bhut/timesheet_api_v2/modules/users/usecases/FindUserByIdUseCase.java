package com.bhut.timesheet_api_v2.modules.users.usecases;

import com.bhut.timesheet_api_v2.exceptions.ResourceNotFoundException;
import com.bhut.timesheet_api_v2.modules.users.entities.UserEntity;
import com.bhut.timesheet_api_v2.modules.users.repository.HoursBankRepository;
import com.bhut.timesheet_api_v2.modules.users.repository.UsersRepository;
import com.bhut.timesheet_api_v2.modules.users.responses.UserResponse;
import org.springframework.stereotype.Service;

import static com.bhut.timesheet_api_v2.modules.users.mappers.UserControllerMapper.MAPPER;
import static java.util.Objects.nonNull;

@Service
public class FindUserByIdUseCase {

    private final UsersRepository usersRepository;
    private final HoursBankRepository hoursBankRepository;

    public FindUserByIdUseCase(final UsersRepository usersRepository, final HoursBankRepository hoursBankRepository) {
        this.usersRepository = usersRepository;
        this.hoursBankRepository = hoursBankRepository;
    }

    public UserResponse execute(final String userId) {
        final var user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found.");
        }
        final var hoursBank = hoursBankRepository.findByUserId(userId);
        user.setHasBankHours(nonNull(hoursBank));
        final var response = MAPPER.toUserResponse(user);
        response.setTotalBankHours(hoursBank.getHour());
        return response;
    }
}
