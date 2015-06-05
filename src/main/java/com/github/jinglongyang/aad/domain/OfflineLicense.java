package com.github.jinglongyang.aad.domain;

public class OfflineLicense {
    private ProductKey productKey;
    private String licenseBlob;
    private String licenseInstanceId;
    private String contentId;
    private String requestorId;

    public ProductKey getProductKey() {
        return productKey;
    }

    public void setProductKey(ProductKey productKey) {
        this.productKey = productKey;
    }

    public String getLicenseBlob() {
        return licenseBlob;
    }

    public void setLicenseBlob(String licenseBlob) {
        this.licenseBlob = licenseBlob;
    }

    public String getLicenseInstanceId() {
        return licenseInstanceId;
    }

    public void setLicenseInstanceId(String licenseInstanceId) {
        this.licenseInstanceId = licenseInstanceId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }
}
