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
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {

    @Schema(description = "This is the id of the user has been created", example = "d883fed5-49fb-435d-b719-e3ce0c8d7cf0")
    private String id;
}
