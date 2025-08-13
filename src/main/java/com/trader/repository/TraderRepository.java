package com.trader.repository;


import com.trader.entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TraderRepository extends JpaRepository<Trader, Long> {
    Optional<Trader> findByContactNameAndAddressLineAndCategory(String name, String address, String category);

    // Check if a trader exists with same category, company name, and address
    boolean existsByCategoryAndCompanyNameAndAddressLine(String category, String companyName, String addressLine);

    // Check for duplicates excluding the current trader by ID (useful for updates)
    boolean existsByCategoryAndCompanyNameAndAddressLineAndTraderIdNot(String category, String companyName, String addressLine, Long traderId);

    // Find traders by pincode
    List<Trader> findByPincode(String pincode);

    // Find traders by category
    List<Trader> findByCategory(String category);





}