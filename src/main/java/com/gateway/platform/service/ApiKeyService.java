package com.gateway.platform.service;

import com.gateway.platform.entity.ApiKeyEntity;
import com.gateway.platform.entity.User;
import com.gateway.platform.repository.ApiKeyRepository;
import com.gateway.platform.repository.UserRepository;
import com.gateway.platform.util.ApiKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final UserRepository userRepository;

    public String createApiKey(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        String key = ApiKeyGenerator.generate();

        ApiKeyEntity apiKey = ApiKeyEntity.builder()
                .keyValue(key)
                .user(user)
                .active(true)
                .build();

        apiKeyRepository.save(apiKey);
        return key;
    }
}
