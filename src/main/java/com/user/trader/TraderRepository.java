package com.user.trader;


import com.user.trader.entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Long> {
    List<Trader> findByPincode(String pincode);
    Optional<Trader> findByPhoneNumber(String phoneNumber);
}
