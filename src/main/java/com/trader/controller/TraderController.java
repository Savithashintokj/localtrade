package com.trader.controller;

import com.trader.dto.TraderRequestDto;
import com.trader.dto.TraderResponseDto;
import com.trader.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traders")
public class TraderController {

    @Autowired
    private TraderService traderService;

    @PostMapping
    public ResponseEntity<TraderResponseDto> createTrader(@RequestBody TraderRequestDto dto){
        return ResponseEntity.ok(traderService.createTrader(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraderResponseDto> getTrader(@PathVariable Long id){
        return ResponseEntity.ok(traderService.getTraderById(id));
    }

    @GetMapping
    public ResponseEntity<List<TraderResponseDto>> getAllTraders(){
        return ResponseEntity.ok(traderService.getAllTraders());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TraderResponseDto>>  searchByPincode(@RequestParam String pincode){
        return ResponseEntity.ok(traderService.searchByPincode(pincode));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<TraderResponseDto>> getTradersByCategory(@PathVariable String category) {
        return ResponseEntity.ok(traderService.getTradersByCategory(category));

    }
    @PutMapping("/{id}")
    public ResponseEntity<TraderResponseDto> updateTrader(@PathVariable Long id, @RequestBody TraderRequestDto dto){
        return ResponseEntity.ok(traderService.updateTrader(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrader(@PathVariable Long id){
        traderService.deleteTrader(id);
        return ResponseEntity.ok("Trader deleted successfully");
    }
}