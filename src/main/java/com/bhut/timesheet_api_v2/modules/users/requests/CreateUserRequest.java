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
public class CreateUserRequest {

    @NotBlank(message = "Name field can not be empty")
    @NotNull(message = "Name field is required")
    @Schema(description = "User name.", example = "John Doe")
    private String name;

    @NotBlank(message = "Email field can not be empty")
    @Schema(description = "User email.", requiredMode = Schema.RequiredMode.REQUIRED, example = "email@domain.com")
    @NotNull(message = "Email field is required")
    @Email(message = "Must declare a valid e-mail")
    private String email;

    @NotBlank(message = "Password field can not be empty")
    @NotNull(message = "Password field is required")
    @Schema(description = "User password.", example = "42208806077")
    private String password;

    @NotBlank(message = "Team field can not be empty")
    @NotNull(message = "Team field is required")
    @Schema(description = "User team.", example = "Bhut team")
    private String team;

    @NotNull(message = "HourValue field is required")
    @Schema(description = "User hour value.", example = "17.05")
    private BigDecimal hourValue;

    @NotNull(message = "HasBankHours field is required")
    @Schema(description = "Define if user has bank hours.", example = "true")
    private Boolean hasBankHours;

    @NotNull(message = "ContractTotal field is required")
    @Schema(description = "User contract total hours.", example = "176")
    private Integer contractTotal;

    @NotBlank(message = "GroupId field can not be empty")
    @NotNull(message = "GroupId field is required")
    @Schema(description = "User group identifier.", example = "6de140ca-ae94-4e1d-9c32-8de18fa22e45")
    private String groupId;

    @NotNull(message = "StartDate field is required")
    @Schema(description = "User start date.", example = "2022-01-04")
    private LocalDate startDate;
}
