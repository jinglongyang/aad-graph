package com.github.jinglongyang.aad.domain;

public class ProductPlatform {
    private String platformName;
    private long minVersion;
    private long maxTested;

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public long getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(long minVersion) {
        this.minVersion = minVersion;
    }

    public long getMaxTested() {
        return maxTested;
    }

    public void setMaxTested(long maxTested) {
        this.maxTested = maxTested;
    }
}
