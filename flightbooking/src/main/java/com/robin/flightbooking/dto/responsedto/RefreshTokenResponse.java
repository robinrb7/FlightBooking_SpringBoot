package com.robin.flightbooking.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
}
