package com.trader.controller;


import com.trader.dto.AuthRequestDto;
import com.trader.dto.AuthResponseDto;
import com.trader.dto.RefreshRequestDto;
import com.trader.service.JwtService;
import com.trader.service.JwtUtil;
import com.trader.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        // Assume OTP is verified
        String accessToken = jwtService.generateAccessToken(request.getMobileNumber());
        String refreshToken = jwtService.generateRefreshToken(request.getMobileNumber());
        refreshTokenService.save(request.getMobileNumber(), refreshToken);
        return ResponseEntity.ok(new AuthResponseDto(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(@RequestBody RefreshRequestDto request) {
        if (!refreshTokenService.isValid(request.getRefreshToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String mobile = refreshTokenService.getMobile(request.getRefreshToken());
        String newAccessToken = jwtService.generateAccessToken(mobile);
        String newRefreshToken = jwtService.generateRefreshToken(mobile);

        refreshTokenService.revoke(request.getRefreshToken());
        refreshTokenService.save(mobile, newRefreshToken);

        return ResponseEntity.ok(new AuthResponseDto(newAccessToken, newRefreshToken));
    }
}
