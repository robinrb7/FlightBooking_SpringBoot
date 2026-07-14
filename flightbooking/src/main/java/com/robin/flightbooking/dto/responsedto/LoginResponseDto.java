package com.robin.flightbooking.dto.responsedto;

import com.robin.flightbooking.entities.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
    private String jwtToken;
    private String userEmail;
    private String refreshToken;
}
