package com.bhut.timesheet_api_v2.modules.timesheet.requests;

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
public class CreateTimesheetRequest {

    private List<CreateTimesheetPeriodRequest> period;
}
