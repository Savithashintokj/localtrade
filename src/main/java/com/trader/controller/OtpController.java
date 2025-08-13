package com.trader.controller;


import com.trader.dto.OtpRequestDto;
import com.trader.dto.OtpResponseDto;
import com.trader.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<OtpResponseDto> generateOtp(@RequestParam Long mobileNumber){
        return ResponseEntity.ok(otpService.generateOtp(mobileNumber));
    }

    @PostMapping("/verify")
    public ResponseEntity<OtpResponseDto> verifyOtp(@RequestBody OtpRequestDto dto){
        return ResponseEntity.ok(otpService.verifyOtp(dto));
    }
}
