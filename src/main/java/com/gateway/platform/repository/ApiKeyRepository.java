package com.gateway.platform.repository;

import com.gateway.platform.entity.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, Long> {
    Optional<ApiKeyEntity> findByKeyValue(String keyValue);
    List<ApiKeyEntity> findByUserId(Long userId);
}
