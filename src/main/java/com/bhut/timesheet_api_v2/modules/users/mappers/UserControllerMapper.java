package com.bhut.timesheet_api_v2.modules.users.mappers;

import com.bhut.timesheet_api_v2.modules.timesheet.entities.ReleaseEntity;
import com.bhut.timesheet_api_v2.modules.timesheet.responses.ReportDaysResponse;
import com.bhut.timesheet_api_v2.modules.timesheet.responses.ReportPeriodResponse;
import com.bhut.timesheet_api_v2.modules.users.entities.HoursBankEntity;
import com.bhut.timesheet_api_v2.modules.users.entities.UserEntity;
import com.bhut.timesheet_api_v2.modules.users.requests.CreateUserHoursBankRequest;
import com.bhut.timesheet_api_v2.modules.users.requests.CreateUserRequest;
import com.bhut.timesheet_api_v2.modules.users.requests.UpdateUserPasswordRequest;
import com.bhut.timesheet_api_v2.modules.users.requests.UpdateUserRequest;
import com.bhut.timesheet_api_v2.modules.users.responses.UserItemResponse;
import com.bhut.timesheet_api_v2.modules.users.responses.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserControllerMapper {

    UserControllerMapper MAPPER = Mappers.getMapper(UserControllerMapper.class);

    @Mapping(target = "id", ignore = true)
    UserEntity toUserEntity(final CreateUserRequest createUserRequest);

    @Mapping(target = "groupName", source = "group.name")
    UserItemResponse toUserItemResponse(final UserEntity userEntity);

    @Mapping(target = "groupName", source = "group.name")
    UserResponse toUserResponse(final UserEntity userEntity);

    UserEntity toUserEntity(final UpdateUserRequest updateUserRequest);

    UserEntity toUserEntity(final UpdateUserPasswordRequest updateUserRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateUserEntity(@MappingTarget final UserEntity userEntity, final UserEntity updatedUserEntity);

    @Mapping(source = "date", target = "date")
    @Mapping(target = "businessDay", expression = "java(!release.getHoliday())")
    @Mapping(target = "period", expression = "java(mapPeriods(release))")
    @Mapping(target = "total", expression = "java(formatTotalHours(release.getTotal()))")
    ReportDaysResponse toReportDaysResponse(ReleaseEntity release);

    default List<ReportPeriodResponse> mapPeriods(ReleaseEntity release) {
        List<ReportPeriodResponse> periods = new ArrayList<>();
        periods.add(new ReportPeriodResponse(release.getStartTime(), release.getEndTime(), release.getDescription()));
        return periods;
    }

    default String formatTotalHours(String totalHours) {
        return totalHours != null ? totalHours : "00:00";
    }

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "hour", source = "createUserHoursBankRequest.balance")
    HoursBankEntity toHoursBankEntity(final String userId, final CreateUserHoursBankRequest createUserHoursBankRequest);
}
