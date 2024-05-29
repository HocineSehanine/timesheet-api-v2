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
public class CloseUserReportResponse {

    private String name;
    private String team;
    private boolean bankHours;
    private ReportCalendarResponse calendar;
    private ReportPayResponse pay;
}
