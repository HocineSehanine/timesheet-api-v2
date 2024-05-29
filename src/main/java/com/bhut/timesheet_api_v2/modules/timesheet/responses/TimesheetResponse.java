package com.bhut.timesheet_api_v2.modules.timesheet.responses;

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
public class TimesheetResponse {

    private boolean closed;
    private int month;
    private int year;
    private String total;
    private double balance;
    private List<ReportDaysResponse> days;
}
