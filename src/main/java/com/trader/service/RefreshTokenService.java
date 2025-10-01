package com.trader.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenService {
    private final Map<String, String> store = new ConcurrentHashMap<>();

    public void save(String mobile, String refreshToken) {
        store.put(refreshToken, mobile);
    }

    public boolean isValid(String refreshToken) {
        return store.containsKey(refreshToken);
    }

    public String getMobile(String refreshToken) {
        return store.get(refreshToken);
    }

    public void revoke(String refreshToken) {
        store.remove(refreshToken);
    }
}

