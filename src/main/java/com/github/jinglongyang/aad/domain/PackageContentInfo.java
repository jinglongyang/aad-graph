package com.github.jinglongyang.aad.domain;

import java.util.List;

public class PackageContentInfo {
    private String packageFormat;
    private List<ProductPlatform> productPlatforms;

    public String getPackageFormat() {
        return packageFormat;
    }

    public void setPackageFormat(String packageFormat) {
        this.packageFormat = packageFormat;
    }

    public List<ProductPlatform> getProductPlatforms() {
        return productPlatforms;
    }

    public void setProductPlatforms(List<ProductPlatform> productPlatforms) {
        this.productPlatforms = productPlatforms;
    }
}
