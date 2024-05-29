package com.bhut.timesheet_api_v2.modules.holiday.mappers;

import com.bhut.timesheet_api_v2.modules.holiday.entities.HolidayEntity;
import com.bhut.timesheet_api_v2.modules.holiday.requests.CreateHolidayRequest;
import com.bhut.timesheet_api_v2.modules.holiday.requests.UpdateHolidayRequest;
import com.bhut.timesheet_api_v2.modules.holiday.responses.ListHolidayResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HolidayControllerMapper {

    HolidayControllerMapper MAPPER = Mappers.getMapper(HolidayControllerMapper.class);

    default List<HolidayEntity> toHolidayEntityList(CreateHolidayRequest request) {
        return request.getDays().stream()
                .map(day -> HolidayEntity.builder()
                        .year(request.getYear())
                        .date(day)
                        .month(day.getMonth().getValue())
                        .build())
                .toList();
    }

    default ListHolidayResponse toListHolidayResponse(List<HolidayEntity> holidayEntities) {
        if (holidayEntities.isEmpty()) {
            return new ListHolidayResponse(0, List.of());
        }

        final var year = holidayEntities.getFirst().getYear();
        final List<String> days = holidayEntities.stream()
                .map(holiday -> holiday.getDate().toString())
                .toList();

        return new ListHolidayResponse(year, days);
    }

    default List<HolidayEntity> toHolidayEntityList(int year, UpdateHolidayRequest updateHolidayRequest) {
        return updateHolidayRequest.getDays().stream()
                .map(day -> HolidayEntity.builder()
                        .year(year)
                        .date(day)
                        .build())
                .toList();
    }
}
