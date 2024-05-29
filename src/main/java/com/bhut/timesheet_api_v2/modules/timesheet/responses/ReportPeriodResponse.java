package com.bhut.timesheet_api_v2.modules.timesheet.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportPeriodResponse {

    @Schema(description = "Start time of pertiod", example = "08:30")
    private String start;

    @Schema(description = "End time of pertiod", example = "17:30")
    private String end;

    @Schema(description = "Description of the day", example = "Implement feature BHUT-2001")
    private String description;
}
