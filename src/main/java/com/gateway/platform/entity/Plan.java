package com.gateway.platform.entity;

public enum Plan {
    FREE(100),
    BASIC(500),
    PREMIUM(1000);

    private final int requestsPerMinute;

    Plan(int requestsPerMinute) {
        this.requestsPerMinute = requestsPerMinute;
    }

    public int getRequestsPerMinute() {
        return requestsPerMinute;
    }
}
