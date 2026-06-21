package com.gateway.platform.util;

import java.security.SecureRandom;
import java.util.Base64;

public class ApiKeyGenerator {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String PREFIX = "sk_";

    private ApiKeyGenerator() {
    }

    /**
     * Generates a random API key like: sk_3f8a9c2e1b...
     * 32 random bytes -> base64url encoded -> prefixed.
     */
    public static String generate() {
        byte[] randomBytes = new byte[32];
        SECURE_RANDOM.nextBytes(randomBytes);
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        return PREFIX + encoded;
    }
}
