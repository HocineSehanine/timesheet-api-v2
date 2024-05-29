package com.bhut.timesheet_api_v2.modules.users.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportCalendarResponse {

    private String month;
    private String year;
    private String total;
}
