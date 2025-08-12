package com.user.auth;


import com.user.auth.dto.OtpVerificationRequest;
import com.user.auth.dto.UserRegistrationRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody OtpVerificationRequest request) {
        return userService.verifyOtp(request);
    }
}
