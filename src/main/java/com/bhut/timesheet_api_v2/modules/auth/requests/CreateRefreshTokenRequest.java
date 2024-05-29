package com.bhut.timesheet_api_v2.modules.auth.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateRefreshTokenRequest {

    @NotBlank(message = "RefreshToken field can not be empty")
    @Schema(description = "Token to be refreshed id", example = "e005d95d-f8a6-4714-b800-a02bb9dbd7bd")
    @NotNull(message = "RefreshToken field is required")
    private String refreshToken;
}
