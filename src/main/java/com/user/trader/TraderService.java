package com.user.trader;

import com.user.auth.entity.User;
import com.user.auth.repository.UserRepository;
import com.user.trader.dto.TraderDto;
import com.user.trader.entity.Trader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TraderService {

    private final TraderRepository traderRepository;
    private final UserRepository userRepository;

    public TraderService(TraderRepository traderRepository, UserRepository userRepository) {
        this.traderRepository = traderRepository;
        this.userRepository = userRepository;
    }

    // Create or Update trader
    public TraderDto saveOrUpdateTrader(TraderDto dto) {
        User user = userRepository.findById(dto.getCreatedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Trader trader = dto.getId() != null
                ? traderRepository.findById(dto.getId())
                .orElse(new Trader())
                : new Trader();
        // Check if phone number exists for a different trader
        Optional<Trader> existingTrader = traderRepository.findByPhoneNumber(dto.getPhoneNumber());
        if (existingTrader.isPresent()) {
            // If updating, allow if existing trader is the same as current
            if (dto.getId() == null || !existingTrader.get().getId().equals(dto.getId())) {
                throw new DuplicatePhoneNumberException("Trader with this phone number already exists.");
            }
        }
        trader.setName(dto.getName());
        trader.setCategory(dto.getCategory());
        trader.setPhoneNumber(dto.getPhoneNumber());
        trader.setAddress(dto.getAddress());
        trader.setCity(dto.getCity());
        trader.setState(dto.getState());
        trader.setPincode(dto.getPincode());
        trader.setDescription(dto.getDescription());
        trader.setCompanyName(dto.getCompanyName());
        trader.setCreatedBy(user);

        Trader saved = traderRepository.save(trader);
        return mapToDto(saved);
    }

    // Get one trader by id
    public TraderDto getTraderById(Long id) {
        Trader trader = traderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trader not found"));
        return mapToDto(trader);
    }

    // Get all traders
    public List<TraderDto> getAllTraders() {
        return traderRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Delete trader by id
    public void deleteTrader(Long id) {
        traderRepository.deleteById(id);
    }

    private TraderDto mapToDto(Trader trader) {
        TraderDto dto = new TraderDto();
        dto.setId(trader.getId());
        dto.setName(trader.getName());
        dto.setCategory(trader.getCategory());
        dto.setPhoneNumber(trader.getPhoneNumber());
        dto.setAddress(trader.getAddress());
        dto.setCity(trader.getCity());
        dto.setState(trader.getState());
        dto.setPincode(trader.getPincode());
        dto.setDescription(trader.getDescription());
        dto.setCompanyName(trader.getCompanyName());
        dto.setCreatedByUserId(trader.getCreatedBy().getId());
        return dto;
    }

    public List<TraderDto> getTradersByPincode(String pincode) {
        List<Trader> traders = traderRepository.findByPincode(pincode);
        return traders.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

}
