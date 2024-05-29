package com.bhut.timesheet_api_v2.modules.users.requests;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class CreateUserHoursBankRequest {

    @NotBlank
    @Schema(description = "Date of bank hour creation", example = "2024-08-25")
    private String date;

    @NotNull
    @Schema(description = "Bank hour balance", example = "10.7")
    private Double balance;

    @NotBlank
    @Schema(description = "Description of bank hour", example = "Lorem ipsum")
    private String description;
}
