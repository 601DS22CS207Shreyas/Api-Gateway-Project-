package com.gateway.platform.controller;

import com.gateway.platform.dto.ApiKeyResponse;
import com.gateway.platform.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apikey")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @PostMapping("/create")
    public ResponseEntity<ApiKeyResponse> createApiKey(Authentication authentication) {
        String username = authentication.getName();
        String apiKey = apiKeyService.createApiKey(username);
        return ResponseEntity.ok(new ApiKeyResponse(apiKey));
    }
}
