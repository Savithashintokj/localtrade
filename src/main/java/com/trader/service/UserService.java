package com.trader.service;


import com.trader.dto.UserRequestDto;
import com.trader.dto.UserResponseDto;
import com.trader.entity.User;
import com.trader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create user with duplicate mobile check
    public UserResponseDto createUser(UserRequestDto dto) {
        if(userRepository.existsByMobileNumber(dto.getMobileNumber())){
            throw new RuntimeException("User with this mobile number already exists");
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMobileNumber(dto.getMobileNumber());
        user.setType(dto.getType());

        User saved = userRepository.save(user);
        return mapToDto(saved);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Duplicate check for mobile number
        if(!user.getMobileNumber().equals(dto.getMobileNumber()) &&
                userRepository.existsByMobileNumber(dto.getMobileNumber())){
            throw new RuntimeException("Mobile number already used by another user");
        }

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMobileNumber(dto.getMobileNumber());
        user.setType(dto.getType());

        User updated = userRepository.save(user);
        return mapToDto(updated);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Map entity to DTO
    private UserResponseDto mapToDto(User user){
        UserResponseDto dto = new UserResponseDto();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setType(user.getType());
        return dto;
    }

    public User getUserByMobile(Long mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
