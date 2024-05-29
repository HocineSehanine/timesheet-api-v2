package com.bhut.timesheet_api_v2.modules.timesheet.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateTimesheetPeriodRequest {

    @Schema(description = "Start of the day", example = "08:30")
    private String start;

    @Schema(description = "End of the day", example = "17:30")
    private String end;

    @Schema(description = "Description of the day", example = "Implement feature BHUT-2001")
    private String description;
}
