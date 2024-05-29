package com.bhut.timesheet_api_v2.modules.users.usecases;

import com.bhut.timesheet_api_v2.exceptions.BusinessRuleException;
import com.bhut.timesheet_api_v2.exceptions.ResourceNotFoundException;
import com.bhut.timesheet_api_v2.modules.group.repository.GroupRepository;
import com.bhut.timesheet_api_v2.modules.users.entities.UserEntity;
import com.bhut.timesheet_api_v2.modules.users.repository.UsersRepository;
import com.bhut.timesheet_api_v2.modules.users.responses.CreateUserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupRepository groupRepository;

    public CreateUserUseCase(final UsersRepository usersRepository, final PasswordEncoder passwordEncoder, final GroupRepository groupRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.groupRepository = groupRepository;
    }

    public CreateUserResponse execute(final UserEntity userEntity) {
        final var user = usersRepository.findByEmail(userEntity.getEmail()).orElse(null);
        if (user != null) {
            throw new BusinessRuleException("This email have been used before.");
        }
        final var group = groupRepository.findById(userEntity.getGroupId()).orElse(null);
        if (group == null) {
            throw new ResourceNotFoundException("Declared group not found.");
        }
        final var password = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);
        final var createdUser = usersRepository.save(userEntity);
        return CreateUserResponse.builder().id(createdUser.getId()).build();
    }
}
