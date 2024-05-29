package com.bhut.timesheet_api_v2.modules.group.controllers;

import com.bhut.timesheet_api_v2.modules.group.responses.GroupResponse;
import com.bhut.timesheet_api_v2.modules.group.usecases.FindAllGroups;
import com.bhut.timesheet_api_v2.modules.users.responses.BaseErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.bhut.timesheet_api_v2.modules.group.mappers.GroupControllerMapper.MAPPER;

@Tag(name = "Group", description = "Resources for managing groups.")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/group")
public class GroupController {

    private final FindAllGroups findAllGroups;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Groups endpoints",
            description = "Get all groups"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Groups successfully listed.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GroupResponse.class)))}),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BaseErrorResponse.class),
                                            examples = {@ExampleObject(name = "INVALID_FIELDS.", value = "{\"code\":\"INVALID FIELDS.\",\"message\":\"Invalid/required fields\"}")}
                                    ),
                            }
                    )
            }
    )
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('a25dd494-0e1e-49b0-8c04-267c59f350a0')")
    public ResponseEntity<Object> findAllUsers(final HttpServletRequest request) {
        try {
            final var groupId = request.getAttribute("group_id").toString();
            final var response = findAllGroups.execute(groupId).stream().map(MAPPER::toGroupResponse);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }
}
