package com.bhut.timesheet_api_v2.modules.users.usecases;

import com.bhut.timesheet_api_v2.exceptions.BusinessRuleException;
import com.bhut.timesheet_api_v2.exceptions.ResourceNotFoundException;
import com.bhut.timesheet_api_v2.modules.group.repository.GroupRepository;
import com.bhut.timesheet_api_v2.modules.users.entities.UserEntity;
import com.bhut.timesheet_api_v2.modules.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

import static com.bhut.timesheet_api_v2.modules.users.mappers.UserControllerMapper.MAPPER;

@Service
public class UpdateUserUseCase {

    private final UsersRepository usersRepository;
    private final GroupRepository groupRepository;

    public UpdateUserUseCase(final UsersRepository usersRepository, final GroupRepository groupRepository) {
        this.usersRepository = usersRepository;
        this.groupRepository = groupRepository;
    }

    public void execute(final String userId, final UserEntity userEntity) {
        final var userToBeUpdated = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        final var checkEmail = usersRepository.findByEmail(userEntity.getEmail()).orElse(null);
        if (checkEmail != null && !userEntity.getEmail().equals(checkEmail.getEmail())) {
            throw new BusinessRuleException("This email have been used before.");
        }
        final var group = groupRepository.findById(userEntity.getGroupId()).orElse(null);
        if (group == null) {
            throw new ResourceNotFoundException("Declared group not found.");
        }
        MAPPER.updateUserEntity(userToBeUpdated, userEntity);
        userEntity.setId(userToBeUpdated.getId());
        usersRepository.save(userToBeUpdated);
    }
}
