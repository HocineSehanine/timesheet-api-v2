package com.bhut.timesheet_api_v2.modules.timesheet.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDaysResponse {

    @Schema(description = "Date of period", example = "2024-08-30")
    private String date;

    @Schema(description = "Is a business day or not", example = "true")
    private boolean businessDay;

    @Schema(description = "The period of the day")
    private List<ReportPeriodResponse> period;

    @Schema(description = "The sum of period", example = "08:00")
    private String total;
}
