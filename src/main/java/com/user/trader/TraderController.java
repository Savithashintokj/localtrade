package com.user.trader;

import com.user.trader.dto.TraderDto;
import com.user.trader.entity.Trader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/traders")
public class TraderController {

    private final TraderService traderService;

    public TraderController(TraderService traderService) {
        this.traderService = traderService;
    }

    @PostMapping
    public ResponseEntity<TraderDto> createTrader(@RequestBody TraderDto traderDto) {

        TraderDto saved = traderService.saveOrUpdateTrader(traderDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TraderDto> updateTrader(@PathVariable Long id, @RequestBody TraderDto traderDto) {
        traderDto.setId(id);
        TraderDto updated = traderService.saveOrUpdateTrader(traderDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraderDto> getTrader(@PathVariable Long id) {
        TraderDto dto = traderService.getTraderById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<TraderDto>> getAllTraders() {
        List<TraderDto> list = traderService.getAllTraders();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrader(@PathVariable Long id) {
        traderService.deleteTrader(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<TraderDto>> searchByPincode(@RequestParam String pincode) {
        List<TraderDto> traders = traderService.getTradersByPincode(pincode);
        return ResponseEntity.ok(traders);
    }

}
