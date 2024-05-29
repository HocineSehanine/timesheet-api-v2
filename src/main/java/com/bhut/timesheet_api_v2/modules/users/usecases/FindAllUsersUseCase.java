package com.bhut.timesheet_api_v2.modules.users.usecases;

import com.bhut.timesheet_api_v2.exceptions.ResourceNotFoundException;
import com.bhut.timesheet_api_v2.exceptions.UnauthorizedException;
import com.bhut.timesheet_api_v2.helpers.CheckGroup;
import com.bhut.timesheet_api_v2.modules.users.entities.UserEntity;
import com.bhut.timesheet_api_v2.modules.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllUsersUseCase {

    private final UsersRepository usersRepository;
    private final CheckGroup checkGroup;

    public FindAllUsersUseCase(final UsersRepository usersRepository, final CheckGroup checkGroup) {
        this.usersRepository = usersRepository;
        this.checkGroup = checkGroup;
    }

    public List<UserEntity> execute(final String userId) {
        final var user = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        if (checkGroup.checkIfAdmin(user.getGroupId())) {
            throw new UnauthorizedException();
        }

        if (usersRepository.findAll().isEmpty()) {
            return List.of();
        }
        return usersRepository.findAll();
    }
}
