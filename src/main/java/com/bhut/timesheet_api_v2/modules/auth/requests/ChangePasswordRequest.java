package com.bhut.timesheet_api_v2.modules.auth.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
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
public class ChangePasswordRequest {

    @Schema(description = "Code to recover passoword", example = "123456")
    @Pattern(regexp = "\\d{6}", message = "Code must be exactly six digits")
    private String code;

    @Schema(description = "The new password", example = "admin123")
    private String newPassword;
}
