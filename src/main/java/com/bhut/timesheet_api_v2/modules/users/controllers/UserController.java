package com.bhut.timesheet_api_v2.modules.users.controllers;


import com.bhut.timesheet_api_v2.helpers.RequestValidator;
import com.bhut.timesheet_api_v2.modules.timesheet.responses.TimesheetResponse;
import com.bhut.timesheet_api_v2.modules.users.requests.CreateUserHoursBankRequest;
import com.bhut.timesheet_api_v2.modules.users.requests.CreateUserRequest;
import com.bhut.timesheet_api_v2.modules.users.requests.UpdateUserPasswordRequest;
import com.bhut.timesheet_api_v2.modules.users.requests.UpdateUserRequest;
import com.bhut.timesheet_api_v2.modules.users.responses.BaseErrorResponse;
import com.bhut.timesheet_api_v2.modules.users.responses.CloseUserReportResponse;
import com.bhut.timesheet_api_v2.modules.users.responses.CreateUserResponse;
import com.bhut.timesheet_api_v2.modules.users.responses.UserItemResponse;
import com.bhut.timesheet_api_v2.modules.users.responses.UserResponse;
import com.bhut.timesheet_api_v2.modules.users.usecases.CloseUserReportUseCase;
import com.bhut.timesheet_api_v2.modules.users.usecases.CreateBankHourUseCase;
import com.bhut.timesheet_api_v2.modules.users.usecases.CreateUserUseCase;
import com.bhut.timesheet_api_v2.modules.users.usecases.FindAllUsersUseCase;
import com.bhut.timesheet_api_v2.modules.users.usecases.FindUserByIdUseCase;
import com.bhut.timesheet_api_v2.modules.users.usecases.GetUserTimesheetReportUseCase;
import com.bhut.timesheet_api_v2.modules.users.usecases.UpdateUserPasswordUseCase;
import com.bhut.timesheet_api_v2.modules.users.usecases.UpdateUserUseCase;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.bhut.timesheet_api_v2.modules.users.mappers.UserControllerMapper.MAPPER;


@Tag(name = "User", description = "Resources for managing users.")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/user")
public class UserController {

    private final RequestValidator requestValidator;
    private final CreateUserUseCase createUserUseCase;
    private final FindAllUsersUseCase findAllUsersUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final CreateBankHourUseCase createBankHourUseCase;
    private final GetUserTimesheetReportUseCase getUserTimesheetReportUseCase;
    private final CloseUserReportUseCase closeUserReportUseCase;

    @PostMapping("/createUser")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Create user",
            description = "Insert a user into the api"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User successfully created.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserResponse.class))}),
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
    public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            requestValidator.validate(createUserRequest);
            final var userEntity = MAPPER.toUserEntity(createUserRequest);
            final var response = createUserUseCase.execute(userEntity);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "List users.",
            description = "Find all users"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Users successfully listed.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserItemResponse.class)))}),
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
            final var userId = request.getAttribute("user_id").toString();
            final var response = findAllUsersUseCase.execute(userId).stream().map(MAPPER::toUserItemResponse);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get user.",
            description = "Find user by id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User successfully founded.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))}),
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
    @PreAuthorize("hasAnyRole('a25dd494-0e1e-49b0-8c04-267c59f350a0', '991a9114-5fa8-4b62-90bf-9b9862539c4b')")
    public ResponseEntity<Object> findUserById(@PathVariable final String id) {
        try {
            final var response = findUserByIdUseCase.execute(id);

            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update user.",
            description = "Insert a user into the api"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
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
    public ResponseEntity<Object> updateUser(@PathVariable final String id, @RequestBody UpdateUserRequest updateUserRequest) {
        try {
            requestValidator.validate(updateUserRequest);
            final var userEntity = MAPPER.toUserEntity(updateUserRequest);
            updateUserUseCase.execute(id, userEntity);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update user.",
            description = "Update user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
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
    @PreAuthorize("hasAnyRole('a25dd494-0e1e-49b0-8c04-267c59f350a0', '991a9114-5fa8-4b62-90bf-9b9862539c4b')")
    public ResponseEntity<Object> updateUserPassword(@PathVariable final String id, @RequestBody UpdateUserPasswordRequest updateUserPasswordRequest) {
        try {
            requestValidator.validate(updateUserPasswordRequest);
            final var userEntity = MAPPER.toUserEntity(updateUserPasswordRequest);
            updateUserPasswordUseCase.execute(id, userEntity);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @PatchMapping("/{id}/bank")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Launch negative hours on database",
            description = "Create user bank hours."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
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
    @PreAuthorize("hasAnyRole('a25dd494-0e1e-49b0-8c04-267c59f350a0', '991a9114-5fa8-4b62-90bf-9b9862539c4b')")
    public ResponseEntity<Object> launchUserReport(
            @PathVariable String id,
            @RequestBody CreateUserHoursBankRequest request,
            final HttpServletRequest httpRequest
    ) {
        try {
            requestValidator.validate(request);
            final var userId = httpRequest.getAttribute("user_id").toString();
            final var hoursBankEntity = MAPPER.toHoursBankEntity(userId, request);
            createBankHourUseCase.execute(id, hoursBankEntity);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @GetMapping("{id}/report/{year}/{month}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get timesheet.",
            description = "Get timesheet."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Timesheet successfully founded.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TimesheetResponse.class)))}),
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
    public ResponseEntity<Object> getTimesheetReport(
            @PathVariable int year,
            @PathVariable int month,
            @PathVariable String id
    ) {
        try {
            final var timesheetResponse = getUserTimesheetReportUseCase.execute(id, year, month);

            return ResponseEntity.ok(timesheetResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @PatchMapping("/{id}/report/{year}/{month}/closed")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get timesheet.",
            description = "Get timesheet."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Timesheet successfully founded.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TimesheetResponse.class)))}),
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
    public ResponseEntity<Object> closeUserReport(
            @PathVariable String id,
            @PathVariable int year,
            @PathVariable int month) {
        final var response = closeUserReportUseCase.closeUserReport(id, year, month);
        return ResponseEntity.ok(response);
    }
}
