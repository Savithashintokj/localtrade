package com.trader.repository;

import com.trader.entity.OtpVerification;
import com.trader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpVerification, Long> {
    Optional<OtpVerification> findByUser(User user);
    Optional<OtpVerification> findTopByUserOrderByCreatedAtDesc(User user);

}
