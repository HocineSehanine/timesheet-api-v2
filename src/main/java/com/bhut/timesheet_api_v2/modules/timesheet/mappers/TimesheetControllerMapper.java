package com.bhut.timesheet_api_v2.modules.timesheet.mappers;

import com.bhut.timesheet_api_v2.modules.timesheet.entities.ReleaseEntity;
import com.bhut.timesheet_api_v2.modules.timesheet.requests.CreateTimesheetPeriodRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface TimesheetControllerMapper {

    TimesheetControllerMapper MAPPER = Mappers.getMapper(TimesheetControllerMapper.class);

    @Mapping(target = "date", source = "date")
    @Mapping(target = "holiday", constant = "false")
    @Mapping(target = "startTime", source = "period.start")
    @Mapping(target = "endTime", source = "period.end")
    @Mapping(target = "description", source = "period.description")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "month", ignore = true)
    ReleaseEntity mapPeriodToReleaseEntity(final String userId, final CreateTimesheetPeriodRequest period, final LocalDate date);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updatedReleaseEntity(@MappingTarget final ReleaseEntity releaseEntity, final ReleaseEntity updatedReleaseEntity);
}
