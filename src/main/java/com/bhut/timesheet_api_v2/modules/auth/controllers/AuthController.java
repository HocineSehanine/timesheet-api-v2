package com.bhut.timesheet_api_v2.modules.auth.controllers;

import com.bhut.timesheet_api_v2.helpers.RequestValidator;
import com.bhut.timesheet_api_v2.modules.auth.requests.ChangePasswordRequest;
import com.bhut.timesheet_api_v2.modules.auth.requests.CreateRefreshTokenRequest;
import com.bhut.timesheet_api_v2.modules.auth.requests.CreateTokenRequest;
import com.bhut.timesheet_api_v2.modules.auth.responses.CreateTokenResponse;
import com.bhut.timesheet_api_v2.modules.auth.usecases.ChangePasswordUseCase;
import com.bhut.timesheet_api_v2.modules.auth.usecases.CreateNewTokenUseCase;
import com.bhut.timesheet_api_v2.modules.auth.usecases.CreateTokenUseCase;
import com.bhut.timesheet_api_v2.modules.auth.usecases.PasswordRecoveryUseCase;
import com.bhut.timesheet_api_v2.modules.group.requests.RecoverPasswordRequest;
import com.bhut.timesheet_api_v2.modules.users.responses.BaseErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Auth", description = "Resources for managing authentications.")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/auth")
public class AuthController {

    private final RequestValidator requestValidator;
    private final CreateTokenUseCase createTokenUseCase;
    private final CreateNewTokenUseCase createNewTokenUseCase;
    private final PasswordRecoveryUseCase passwordRecoveryUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Generate token endpoints",
            description = "Generate token to access api"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User successfully generated.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CreateTokenResponse.class))}),
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
    public ResponseEntity<Object> createToken(@RequestBody CreateTokenRequest createTokenRequest) {
        try {
            requestValidator.validate(createTokenRequest);
            final var response = createTokenUseCase.execute(createTokenRequest);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Refresh token endpoints.",
            description = "Refresh old token."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User successfully refreshed.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CreateTokenResponse.class))}),
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
    public ResponseEntity<Object> createRefreshToken(@RequestBody CreateRefreshTokenRequest createTokenRequest) {
        try {
            requestValidator.validate(createTokenRequest);
            final var response = createNewTokenUseCase.execute(createTokenRequest.getRefreshToken());
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @PostMapping("recover-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Recover password.",
            description = "Recover password."
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
    public ResponseEntity<Object> requestPasswordRecovery(@RequestBody RecoverPasswordRequest request) {
        try {
            requestValidator.validate(request);
            passwordRecoveryUseCase.requestPasswordRecovery(request.getEmail());
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }

    @PutMapping("recover-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Change password.",
            description = "Change password."
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
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            requestValidator.validate(request);
            changePasswordUseCase.execute(request.getCode(), request.getNewPassword());
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseErrorResponse.builder().code("INVALID FIELDS.").message(e.getMessage()).build());
        }
    }
}
