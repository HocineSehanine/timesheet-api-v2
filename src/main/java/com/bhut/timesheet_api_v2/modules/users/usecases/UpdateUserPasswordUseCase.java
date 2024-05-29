package com.bhut.timesheet_api_v2.modules.users.usecases;

import com.bhut.timesheet_api_v2.exceptions.ResourceNotFoundException;
import com.bhut.timesheet_api_v2.modules.users.entities.UserEntity;
import com.bhut.timesheet_api_v2.modules.users.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.bhut.timesheet_api_v2.modules.users.mappers.UserControllerMapper.MAPPER;

@Service
public class UpdateUserPasswordUseCase {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserPasswordUseCase(final UsersRepository usersRepository, final PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(final String userId, UserEntity userEntity) {
        final var userToBeUpdated = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        final var newPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(newPassword);
        MAPPER.updateUserEntity(userToBeUpdated, userEntity);

        userEntity.setId(userToBeUpdated.getId());
        usersRepository.save(userToBeUpdated);
    }
}
