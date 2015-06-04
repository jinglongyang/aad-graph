package com.github.jinglongyang.aad.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductKey {
    @JsonProperty("productId")
    private String productId;

    @JsonProperty("skuId")
    private String skuId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
