package com.user.auth;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    private final Map<String, User> userStore = new HashMap<>();
    private final Random random = new Random();

    public String registerUser(UserRegistrationRequest request) {
        if (userStore.containsKey(request.getMobileNumber())) {
            return "User already registered. Please verify OTP.";
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMobileNumber(request.getMobileNumber());

        String otp = generateOtp();
        user.setOtp(otp);
        user.setVerified(false);

        userStore.put(request.getMobileNumber(), user);

        // Simulate sending OTP (replace with actual SMS API in real app)
        System.out.println("OTP for " + request.getMobileNumber() + " is: " + otp);

        return "User registered. OTP sent to mobile.";
    }

    public String verifyOtp(OtpVerificationRequest request) {
        User user = userStore.get(request.getMobileNumber());
        if (user == null) {
            return "User not found.";
        }

        if (user.getOtp().equals(request.getOtp())) {
            user.setVerified(true);
            return "OTP verified. Login successful.";
        } else {
            return "Invalid OTP.";
        }
    }

    private String generateOtp() {
        return String.format("%06d", random.nextInt(999999));
    }
}
