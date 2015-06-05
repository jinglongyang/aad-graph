package com.github.jinglongyang.aad.domain;

import java.util.List;

public class ProductPackageDetail {
    private String packageSetId;
    private List<ProductPackage> productPackages;

    public String getPackageSetId() {
        return packageSetId;
    }

    public void setPackageSetId(String packageSetId) {
        this.packageSetId = packageSetId;
    }

    public List<ProductPackage> getProductPackages() {
        return productPackages;
    }

    public void setProductPackages(List<ProductPackage> productPackages) {
        this.productPackages = productPackages;
    }
}
