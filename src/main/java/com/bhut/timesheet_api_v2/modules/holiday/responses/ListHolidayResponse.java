package com.bhut.timesheet_api_v2.modules.holiday.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListHolidayResponse {

    @Schema(description = "Holiday year", example = "2024")
    private int year;

    @Schema(description = "Holiday list", example = "[\"2024-12-25\"]")
    private List<String> days;
}
