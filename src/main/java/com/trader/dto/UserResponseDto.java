package com.trader.dto;

import java.time.LocalDateTime;

public class UserResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private Long mobileNumber;
    private String type;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    private LocalDateTime createdAt;

    // getters & setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
