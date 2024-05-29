package com.bhut.timesheet_api_v2.modules.auth.usecases;

import com.bhut.timesheet_api_v2.exceptions.ResourceNotFoundException;
import com.bhut.timesheet_api_v2.modules.auth.repository.PasswordRecoveryRepository;
import com.bhut.timesheet_api_v2.modules.users.repository.UsersRepository;
import com.bhut.timesheet_api_v2.modules.users.requests.UpdateUserPasswordRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.bhut.timesheet_api_v2.modules.users.mappers.UserControllerMapper.MAPPER;

@Service
public class ChangePasswordUseCase {

    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordUseCase(final PasswordRecoveryRepository passwordRecoveryRepository, final UsersRepository usersRepository, final PasswordEncoder passwordEncoder) {
        this.passwordRecoveryRepository = passwordRecoveryRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(final String code, final String password) {

        final var recoveryEntity = passwordRecoveryRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Invalid recovery code"));
        final var userToBeUpdated = usersRepository.findById(recoveryEntity.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var userEntity = MAPPER.toUserEntity(UpdateUserPasswordRequest.builder().password(password).build());
        final var newPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(newPassword);
        MAPPER.updateUserEntity(userToBeUpdated, userEntity);
        usersRepository.save(userToBeUpdated);
        passwordRecoveryRepository.delete(recoveryEntity);
    }
}
