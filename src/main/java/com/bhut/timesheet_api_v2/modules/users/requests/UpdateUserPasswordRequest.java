package com.bhut.timesheet_api_v2.modules.users.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordRequest {

    @NotBlank(message = "Password field can not be empty")
    @NotNull(message = "Password field is required")
    @Schema(description = "User password.", example = "42208806077")
    private String password;
}
