package com.bhut.timesheet_api_v2.modules.group.mappers;

import com.bhut.timesheet_api_v2.modules.group.entities.GroupEntity;
import com.bhut.timesheet_api_v2.modules.group.responses.GroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GroupControllerMapper {

    GroupControllerMapper MAPPER = Mappers.getMapper(GroupControllerMapper.class);

    GroupResponse toGroupResponse(final GroupEntity groupEntity);
}
