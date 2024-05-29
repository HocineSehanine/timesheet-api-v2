package com.bhut.timesheet_api_v2.modules.auth.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateTokenRequest {

    @NotBlank(message = "Email field can not be empty")
    @Schema(description = "User email.", requiredMode = Schema.RequiredMode.REQUIRED, example = "email@domain.com")
    @NotNull(message = "Email field is required")
    @Email(message = "Must declare a valid e-mail")
    private String email;

    @NotBlank(message = "Password field can not be empty")
    @NotNull(message = "Password field is required")
    @Schema(description = "User password.", example = "42208806077")
    private String password;
}
