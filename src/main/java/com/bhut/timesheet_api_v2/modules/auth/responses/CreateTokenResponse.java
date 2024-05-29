package com.bhut.timesheet_api_v2.modules.auth.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenResponse {

    @Schema(description = "This is an access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String accessToken;

    @Schema(description = "This is a refreshToken uuid when accessToken have been expired", example = "ae54d63e-0652-488d-9982-8ac49872f2da")
    private String refreshToken;

    @Schema(description = "This is a token type", example = "Bearer")
    private String tokenType;

    @Schema(description = "This is the period of expiration of access token", example = "86400")
    private Long expiresIn;
}
