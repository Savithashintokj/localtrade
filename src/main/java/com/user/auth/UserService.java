package com.user.auth;


import com.user.auth.dto.OtpVerificationRequest;
import com.user.auth.dto.UserRegistrationRequest;
import com.user.auth.entity.User;
import com.user.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Random random = new Random();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(UserRegistrationRequest request) {
        if (userRepository.findByMobileNumber(request.getMobileNumber()).isPresent()) {
            return "User already registered. Please verify OTP.";
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMobileNumber(request.getMobileNumber());

        String otp = generateOtp();
        user.setOtp(otp);
        user.setVerified(false);

        userRepository.save(user);

        // Simulate sending OTP (replace with SMS provider in production)
        System.out.println("OTP for " + request.getMobileNumber() + " is: " + otp);

        return "User registered. OTP sent to mobile.";
    }

    public String verifyOtp(OtpVerificationRequest request) {
        User user = userRepository.findByMobileNumber(request.getMobileNumber())
                .orElse(null);

        if (user == null) {
            return "User not found.";
        }

        if (user.getOtp().equals(request.getOtp())) {
            user.setVerified(true);
            userRepository.save(user);
            return "OTP verified. Login successful.";
        } else {
            return "Invalid OTP.";
        }
    }

    private String generateOtp() {
        return String.format("%06d", random.nextInt(999999));
    }
}
