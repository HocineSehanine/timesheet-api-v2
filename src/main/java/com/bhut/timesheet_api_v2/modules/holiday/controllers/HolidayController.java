package com.bhut.timesheet_api_v2.modules.holiday.controllers;

import com.bhut.timesheet_api_v2.helpers.RequestValidator;
import com.bhut.timesheet_api_v2.modules.holiday.requests.CreateHolidayRequest;
import com.bhut.timesheet_api_v2.modules.holiday.requests.UpdateHolidayRequest;
import com.bhut.timesheet_api_v2.modules.holiday.responses.ListHolidayResponse;
import com.bhut.timesheet_api_v2.modules.holiday.usecases.CreateHolidayUseCase;
import com.bhut.timesheet_api_v2.modules.holiday.usecases.ListHolidayUseCase;
import com.bhut.timesheet_api_v2.modules.holiday.usecases.UpdateHolidayUseCase;
import com.bhut.timesheet_api_v2.modules.users.responses.BaseErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.bhut.timesheet_api_v2.modules.holiday.mappers.HolidayControllerMapper.MAPPER;

@Tag(name = "Holiday", description = "Resources for managing holidays.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/holiday")
public class HolidayController {

    private final RequestValidator requestValidator;
    private final CreateHolidayUseCase createHolidayUseCase;
    private final ListHolidayUseCase listHolidayUseCase;
    private final UpdateHolidayUseCase updateHolidayUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Create holidays",
            description = "Insert a holidays into the api"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Holidays successfully listed."),
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
    public ResponseEntity<Object> createHoliday(@RequestBody final CreateHolidayRequest request) {
        try {
            requestValidator.validate(request);
            final var holidayEntities = MAPPER.toHolidayEntityList(request);
            createHolidayUseCase.execute(holidayEntities);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "List holidays",
            description = "List holidays of actual yeah"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Holidays successfully listed.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ListHolidayResponse.class))}),
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
    public ResponseEntity<Object> listHoliday() {
        try {
            final var holidayEntities = listHolidayUseCase.execute();
            final var holidays = MAPPER.toListHolidayResponse(holidayEntities);
            return ResponseEntity.ok().body(holidays);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @PutMapping("/{year}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Update holidays",
            description = "update holidays"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Holidays successfully updated."),
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
    public ResponseEntity<Object> updateHoliday(@PathVariable final int year, @RequestBody final UpdateHolidayRequest request) {
        try {
            final var holidayEntities = MAPPER.toHolidayEntityList(year, request);
            updateHolidayUseCase.execute(holidayEntities);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }
}
