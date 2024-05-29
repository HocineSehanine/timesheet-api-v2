package com.bhut.timesheet_api_v2.modules.auth.usecases;

import com.bhut.timesheet_api_v2.exceptions.ResourceNotFoundException;
import com.bhut.timesheet_api_v2.modules.auth.entities.PasswordRecoveryEntity;
import com.bhut.timesheet_api_v2.modules.auth.repository.PasswordRecoveryRepository;
import com.bhut.timesheet_api_v2.modules.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.security.SecureRandom;

@Service
public class PasswordRecoveryUseCase {
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final UsersRepository usersRepository;
    private final SecureRandom random = new SecureRandom();

    public PasswordRecoveryUseCase(final PasswordRecoveryRepository passwordRecoveryRepository, final UsersRepository usersRepository) {
        this.passwordRecoveryRepository = passwordRecoveryRepository;
        this.usersRepository = usersRepository;
    }

    public void requestPasswordRecovery(String email) {
        final var user = usersRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        final var recoveryEntity = PasswordRecoveryEntity.builder()
                .userId(user.getId())
                .code(this.generateRandomCode())
                .build();
        passwordRecoveryRepository.save(recoveryEntity);
    }

    private String generateRandomCode() {
        int min = 100000;
        int max = 999999;
        int code = min + random.nextInt(max - min + 1);
        return String.valueOf(code);
    }
}
