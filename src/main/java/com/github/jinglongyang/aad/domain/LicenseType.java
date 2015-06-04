package com.github.jinglongyang.aad.domain;

public enum LicenseType {
    ONLINE("online"), OFFLINE("offline");

    private final String value;

    private LicenseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
