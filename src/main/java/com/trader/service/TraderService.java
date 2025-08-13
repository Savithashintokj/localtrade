package com.trader.service;

import com.trader.dto.TraderRequestDto;
import com.trader.dto.TraderResponseDto;
import com.trader.entity.Trader;
import com.trader.entity.User;
import com.trader.repository.TraderRepository;
import com.trader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraderService {

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private UserRepository userRepository;

    // Create Trader + associated User automatically
    public TraderResponseDto createTrader(TraderRequestDto dto) {

        // Check duplicate trader: same category + name + address
        boolean exists = traderRepository.existsByCategoryAndCompanyNameAndAddressLine(
                dto.getCategory(), dto.getCompanyName(), dto.getAddressLine()
        );
        if(exists){
            throw new RuntimeException("Trader already exists with same name, address, and category");
        }
        User createdUser = userRepository.getById(dto.getCreatedByUser());
        // Create or find User
        User user = userRepository.findByMobileNumber(dto.getMobileNumber())
                .orElseGet(() -> {
                    User u = new User();
                    u.setFirstName(dto.getFirstName());
                    u.setLastName(dto.getLastName());
                    u.setMobileNumber(dto.getMobileNumber());
                    u.setType("T"); // T = Trader
                    return userRepository.save(u);
                });

        Trader trader = new Trader();
        trader.setCompanyName(dto.getCompanyName());
        trader.setCategory(dto.getCategory());
        trader.setContactName(dto.getContactName());
        trader.setFirstName(dto.getFirstName());
        trader.setLastName(dto.getLastName());
        trader.setMobileNumber(dto.getMobileNumber());
        trader.setAddressLine(dto.getAddressLine());
        trader.setCity(dto.getCity());
        trader.setState(dto.getState());
        trader.setPincode(dto.getPincode());
        trader.setDescription(dto.getDescription());
        trader.setCreatedByUser(createdUser);
        trader.setLinkedUser(user);

        Trader saved = traderRepository.save(trader);
        return mapToDto(saved);
    }

    public TraderResponseDto getTraderById(Long id){
        Trader trader = traderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trader not found"));
        return mapToDto(trader);
    }

    public List<TraderResponseDto> getAllTraders(){
        return traderRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<TraderResponseDto> searchByPincode(String pincode){
        return traderRepository.findByPincode(pincode).stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<TraderResponseDto> getTradersByCategory(String category){
        return traderRepository.findByCategory(category).stream()
                .map(this::mapToDto)
                .toList();
    }


    public TraderResponseDto updateTrader(Long id, TraderRequestDto dto){
        Trader trader = traderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trader not found"));

        // Duplicate check
        boolean exists = traderRepository.existsByCategoryAndCompanyNameAndAddressLineAndTraderIdNot(
                dto.getCategory(), dto.getCompanyName(), dto.getAddressLine(), id
        );
        if(exists){
            throw new RuntimeException("Trader already exists with same name, address, and category");
        }

        trader.setCompanyName(dto.getCompanyName());
        trader.setCategory(dto.getCategory());
        trader.setContactName(dto.getContactName());
        trader.setFirstName(dto.getFirstName());
        trader.setLastName(dto.getLastName());
        trader.setMobileNumber(dto.getMobileNumber());
        trader.setAddressLine(dto.getAddressLine());
        trader.setCity(dto.getCity());
        trader.setState(dto.getState());
        trader.setPincode(dto.getPincode());
        trader.setDescription(dto.getDescription());

        Trader updated = traderRepository.save(trader);
        return mapToDto(updated);
    }

    public void deleteTrader(Long id){
        traderRepository.deleteById(id);
    }

    private TraderResponseDto mapToDto(Trader trader){
        TraderResponseDto dto = new TraderResponseDto();
        dto.setTraderId(trader.getTraderId());
        dto.setCompanyName(trader.getCompanyName());
        dto.setCategory(trader.getCategory());
        dto.setContactName(trader.getContactName());
        dto.setFirstName(trader.getFirstName());
        dto.setLastName(trader.getLastName());
        dto.setMobileNumber(trader.getMobileNumber());
        dto.setAddressLine(trader.getAddressLine());
        dto.setCity(trader.getCity());
        dto.setState(trader.getState());
        dto.setPincode(trader.getPincode());
        dto.setDescription(trader.getDescription());
        dto.setCreatedByUser(trader.getCreatedByUser().getUserId());
        dto.setTraderUserId(trader.getLinkedUser().getUserId());
        dto.setCreatedAt(trader.getCreatedAt());
        return dto;
    }
}
