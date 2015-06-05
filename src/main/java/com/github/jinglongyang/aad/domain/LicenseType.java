package com.github.jinglongyang.aad.domain;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LicenseType {
    ONLINE("online"), OFFLINE("offline");

    private final String value;

    LicenseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static LicenseType fromValue(String value) {
        if (StringUtils.equalsIgnoreCase(ONLINE.getValue(), value)) {
            return ONLINE;
        }
        if (StringUtils.equalsIgnoreCase(OFFLINE.getValue(), value)) {
            return OFFLINE;
        }
        throw new IllegalArgumentException(String.format("Invalid value %s, it can only be online and offline", value));
    }
}
