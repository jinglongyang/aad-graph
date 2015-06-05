package com.github.jinglongyang.aad.domain;

import java.util.List;

public class ProductDetail {
    private ProductKey productKey;
    private String productType;
    private String publisherId;
    private String category;
    private String packageFamilyName;
    private List<String> supportedMarkets;
    private String productDetailsVersion;
    private String etag;
    private List<ContentDetail> contentDetails;
    private List<AlternateId> alternateIds;

    public ProductKey getProductKey() {
        return productKey;
    }

    public void setProductKey(ProductKey productKey) {
        this.productKey = productKey;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPackageFamilyName() {
        return packageFamilyName;
    }

    public void setPackageFamilyName(String packageFamilyName) {
        this.packageFamilyName = packageFamilyName;
    }

    public List<String> getSupportedMarkets() {
        return supportedMarkets;
    }

    public void setSupportedMarkets(List<String> supportedMarkets) {
        this.supportedMarkets = supportedMarkets;
    }

    public String getProductDetailsVersion() {
        return productDetailsVersion;
    }

    public void setProductDetailsVersion(String productDetailsVersion) {
        this.productDetailsVersion = productDetailsVersion;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public List<ContentDetail> getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(List<ContentDetail> contentDetails) {
        this.contentDetails = contentDetails;
    }

    public List<AlternateId> getAlternateIds() {
        return alternateIds;
    }

    public void setAlternateIds(List<AlternateId> alternateIds) {
        this.alternateIds = alternateIds;
    }
}
