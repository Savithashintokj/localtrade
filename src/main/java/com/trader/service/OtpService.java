package com.trader.service;

import com.trader.dto.OtpRequestDto;
import com.trader.dto.OtpResponseDto;
import com.trader.entity.OtpVerification;
import com.trader.entity.User;
import com.trader.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserService userService;

    public OtpResponseDto generateOtp(Long mobileNumber) {
        User user = userService.getUserByMobile(mobileNumber);

        String otp = String.format("%06d", new Random().nextInt(999999));

        OtpVerification verification = new OtpVerification();
        verification.setUser(user);
        verification.setMobileNumber(mobileNumber);
        verification.setOtpCode(otp);
        verification.setOtpStatus("SENT");
        verification.setVerificationStatus("PENDING");

        OtpVerification saved = otpRepository.save(verification);
        return mapToDto(saved);
    }

    public boolean verifyOtp(OtpRequestDto dto) {
        User user = userService.getUserByMobile(dto.getMobileNumber());

        Optional<OtpVerification> latestOtp = otpRepository.findTopByUserOrderByCreatedAtDesc(user);
        if (latestOtp.isEmpty()) {
            throw new RuntimeException("OTP not found for this user");
        }

        OtpVerification otpVerification = latestOtp.get();

        if (!otpVerification.getOtpCode().equals(dto.getOtpCode())) {
            otpVerification.setVerificationStatus("FAILED");
            otpRepository.save(otpVerification);
            throw new RuntimeException("Invalid OTP");
        }

        otpVerification.setVerificationStatus("VERIFIED");
        otpRepository.save(otpVerification);
        return true;
    }

    private OtpResponseDto mapToDto(OtpVerification verification) {
        OtpResponseDto dto = new OtpResponseDto();
        dto.setOtpId(verification.getOtpId());
        dto.setUserId(verification.getUser().getUserId());
        dto.setMobileNumber(verification.getMobileNumber());
        dto.setOtpStatus(verification.getOtpStatus());
        dto.setVerificationStatus(verification.getVerificationStatus());
        dto.setCreatedAt(verification.getCreatedAt());
        dto.setOtpCode(verification.getOtpCode());
        return dto;
    }
}
