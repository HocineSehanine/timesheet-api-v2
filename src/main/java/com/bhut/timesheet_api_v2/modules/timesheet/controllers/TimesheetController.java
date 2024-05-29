package com.bhut.timesheet_api_v2.modules.timesheet.controllers;

import com.bhut.timesheet_api_v2.helpers.RequestValidator;
import com.bhut.timesheet_api_v2.modules.timesheet.requests.CreateTimesheetRequest;
import com.bhut.timesheet_api_v2.modules.timesheet.responses.TimesheetResponse;
import com.bhut.timesheet_api_v2.modules.timesheet.uasecases.CreateTimesheetUseCase;
import com.bhut.timesheet_api_v2.modules.timesheet.uasecases.GetTimesheetUseCase;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static com.bhut.timesheet_api_v2.modules.timesheet.mappers.TimesheetControllerMapper.MAPPER;

@Tag(name = "Timesheet", description = "Resources for managing timesheet.")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/timesheet")
public class TimesheetController {

    private final RequestValidator requestValidator;
    private final CreateTimesheetUseCase createTimesheetUseCase;
    private final GetTimesheetUseCase getTimesheetUseCase;

    @PostMapping("/{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Create timesheet.",
            description = "Create timesheet."
    )
    @ApiResponses(
            value = {
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
    ResponseEntity<Object> updateTimesheet(
            @PathVariable final int year,
            @PathVariable final int month,
            @PathVariable final int day,
            @RequestBody CreateTimesheetRequest request,
            final HttpServletRequest httpRequest
    ) {
        try {
            requestValidator.validate(request);
            final var userId = httpRequest.getAttribute("user_id").toString();
            final var date = LocalDate.of(year, month, day);
            final var timesheetEntity = MAPPER.mapPeriodToReleaseEntity(userId, request.getPeriod().getFirst(), date);
            createTimesheetUseCase.execute(timesheetEntity, date);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @GetMapping("/{year}/{month}")
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
    @PreAuthorize("hasAnyRole('a25dd494-0e1e-49b0-8c04-267c59f350a0', '991a9114-5fa8-4b62-90bf-9b9862539c4b')")
    public ResponseEntity<Object> getTimesheetReport(
            @PathVariable int year,
            @PathVariable int month,
            final HttpServletRequest httpRequest
    ) {
        try {
            final var userId = httpRequest.getAttribute("user_id").toString();

            final var timesheetResponse = getTimesheetUseCase.execute(userId, year, month);

            return ResponseEntity.ok(timesheetResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }
}
