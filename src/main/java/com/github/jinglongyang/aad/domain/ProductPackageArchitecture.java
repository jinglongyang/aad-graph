package com.github.jinglongyang.aad.domain;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProductPackageArchitecture {
    X64("x64"), X86("x86"), ARM("arm");
    private final String value;

    ProductPackageArchitecture(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ProductPackageArchitecture fromValue(String value) {
        if (StringUtils.equalsIgnoreCase(X64.getValue(), value)) {
            return X64;
        }
        if (StringUtils.equalsIgnoreCase(X86.getValue(), value)) {
            return X86;
        }
        if (StringUtils.equalsIgnoreCase(ARM.getValue(), value)) {
            return ARM;
        }
        throw new IllegalArgumentException(String.format("Invalid value %s, it can only be x64, x86 and arm", value));
    }
}
