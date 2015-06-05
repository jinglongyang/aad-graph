package com.github.jinglongyang.aad.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InventoryEntry {
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    private ProductKey productKey;
    private long availableSeats;
    private long seatCapacity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private Date lastModified;
    private LicenseType licenseType;

    public ProductKey getProductKey() {
        return productKey;
    }

    public void setProductKey(ProductKey productKey) {
        this.productKey = productKey;
    }

    public long getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(long availableSeats) {
        this.availableSeats = availableSeats;
    }

    public long getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(long seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }
}
