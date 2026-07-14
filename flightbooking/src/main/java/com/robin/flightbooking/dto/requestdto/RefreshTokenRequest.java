package com.robin.flightbooking.dto.requestdto;

import com.robin.flightbooking.entities.RefreshToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {
    private String refreshToken;
}
