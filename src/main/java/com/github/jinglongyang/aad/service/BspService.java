package com.github.jinglongyang.aad.service;

import java.util.Date;

import com.github.jinglongyang.aad.domain.AuthenticationRequest;
import com.github.jinglongyang.aad.domain.InventoryEntries;
import com.github.jinglongyang.aad.domain.LicenseType;
import com.github.jinglongyang.aad.domain.OfflineLicense;
import com.github.jinglongyang.aad.domain.ProductDetail;
import com.github.jinglongyang.aad.domain.ProductPackageDetail;

public interface BspService {
    InventoryEntries getInventory(AuthenticationRequest request, LicenseType licenseType, Date modifiedSince, String continuationToken, Integer maxResults);

    ProductDetail getProductDetail(AuthenticationRequest request, String productId, String skuId);
    
    OfflineLicense getOfflineLicense(AuthenticationRequest request, String productId, String skuId, String contentId);

    ProductPackageDetail getProductPackage(AuthenticationRequest request, String productId, String skuId);
}
