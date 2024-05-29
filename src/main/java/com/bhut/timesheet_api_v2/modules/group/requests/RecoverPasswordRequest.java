package com.bhut.timesheet_api_v2.modules.group.requests;

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
public class RecoverPasswordRequest {

    @Schema(description = "Email to recover password", example = "email@domain.com")
    private String email;
}
