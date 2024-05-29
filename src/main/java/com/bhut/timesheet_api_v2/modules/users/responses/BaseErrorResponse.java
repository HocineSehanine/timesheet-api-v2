package com.bhut.timesheet_api_v2.modules.users.responses;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class BaseErrorResponse {

    @Schema(description = "Operation error code.", example = "INVALID_FIELD")
    private String code;

    @Schema(description = "Operation error message.", example = "Invalid/required fields")
    private String message;
}
