package com.bhut.timesheet_api_v2.modules.holiday.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHolidayRequest {

    @Schema(description = "Holiday list", example = "[\"2024-12-25\"]")
    private List<LocalDate> days;
}
