package com.bhut.timesheet_api_v2.modules.users.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UpdateUserRequest {

    @Schema(description = "User name.", example = "John Doe")
    private String name;

    @Schema(description = "User email.", example = "email@domain.com")
    @Email(message = "Must declare a valid e-mail")
    private String email;

    @Schema(description = "User team.", example = "Bhut team")
    private String team;

    @Schema(description = "User hour value.", example = "17.05")
    private BigDecimal hourValue;

    @Schema(description = "Define if user has bank hours.", example = "true")
    private Boolean hasBankHours;

    @Schema(description = "User contract total hours.", example = "176")
    private Integer contractTotal;

    @Schema(description = "User group identifier.", example = "6de140ca-ae94-4e1d-9c32-8de18fa22e45")
    private String groupId;

    @Schema(description = "User start date.", example = "2022-01-04")
    private LocalDate startDate;
}
