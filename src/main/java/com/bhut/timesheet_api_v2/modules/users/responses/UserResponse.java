package com.bhut.timesheet_api_v2.modules.users.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @Schema(description = "This is the id of the user", example = "d883fed5-49fb-435d-b719-e3ce0c8d7cf0")
    private String id;

    @Schema(description = "The name of the user", example = "John Doe")
    private String name;

    @Schema(description = "The email of the user", example = "email@domain.com")
    private String email;

    @Schema(description = "The team of the user", example = "Bhut team")
    private String team;

    @Schema(description = "The hourly value for the user", example = "17.05")
    private Double hourValue;

    @Schema(description = "Flag indicating if the user has bank hours", example = "true")
    private Boolean hasBankHours;

    @Schema(description = "Total bank hours for the user", example = "9.51")
    private String totalBankHours;

    @Schema(description = "Total contract hours for the user", example = "176")
    private Integer contractTotal;

    @Schema(description = "The group ID the user belongs to", example = "6de140ca-ae94-4e1d-9c32-8de18fa22e45")
    private String groupId;

    @Schema(description = "The name of the group the user belongs to", example = "Developer")
    private String groupName;

    @Schema(description = "The start date of user",  example = "2022-01-04")
    private LocalDate startDate;
}
