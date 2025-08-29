package com.trader.controller;


import com.trader.dto.AuthResponseDto;
import com.trader.dto.OtpRequestDto;
import com.trader.dto.OtpResponseDto;
import com.trader.service.JwtService;
import com.trader.service.OtpService;
import com.trader.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/generate")
    public ResponseEntity<OtpResponseDto> generateOtp(@RequestParam Long mobileNumber){
        return ResponseEntity.ok(otpService.generateOtp(mobileNumber));
    }


    @PostMapping("/verify")
    public ResponseEntity<AuthResponseDto> verifyOtp(@RequestBody OtpRequestDto dto) {
        boolean isValid = otpService.verifyOtp(dto); // returns true if OTP is correct

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String mobile = dto.getMobileNumber().toString();
        String accessToken = jwtService.generateAccessToken(mobile);
        String refreshToken = jwtService.generateRefreshToken(mobile);
        refreshTokenService.save(mobile, refreshToken);

        return ResponseEntity.ok(new AuthResponseDto(accessToken, refreshToken));
    }

}
