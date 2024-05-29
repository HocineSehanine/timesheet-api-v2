package com.bhut.timesheet_api_v2.modules.users.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportPayResponse {

    private LocalDate date;
    private double hour;
    private String total;
}
