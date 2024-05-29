package com.bhut.timesheet_api_v2.modules.auth.mappers;

import com.bhut.timesheet_api_v2.modules.auth.entities.PasswordRecoveryEntity;
import com.bhut.timesheet_api_v2.modules.group.requests.RecoverPasswordRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PasswordRecoveryMapper {

    PasswordRecoveryMapper MAPPER = Mappers.getMapper(PasswordRecoveryMapper.class);

    PasswordRecoveryEntity toPasswordRecoveryEntity(RecoverPasswordRequest request);
}
