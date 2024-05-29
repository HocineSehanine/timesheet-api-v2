package com.bhut.timesheet_api_v2.modules.holiday.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHolidayRequest {

    @Schema(description = "Year of holiday", example = "2024")
    private int year;
    @Schema(description = "List of holiday dates in ISO-8601 format (YYYY-MM-DD)", example = "[\"2024-01-01\", \"2024-12-25\"]")
    private List<LocalDate> days;
}
