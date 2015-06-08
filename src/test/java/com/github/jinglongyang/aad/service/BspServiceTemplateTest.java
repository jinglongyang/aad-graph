package com.github.jinglongyang.aad.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.github.jinglongyang.aad.domain.AuthenticationRequest;
import com.github.jinglongyang.aad.domain.InventoryEntries;
import com.github.jinglongyang.aad.domain.InventoryEntry;
import com.github.jinglongyang.aad.domain.LicenseType;
import com.github.jinglongyang.aad.domain.LocalizedProductDetail;
import com.github.jinglongyang.aad.domain.OfflineLicense;
import com.github.jinglongyang.aad.domain.ProductDetail;
import com.github.jinglongyang.aad.domain.ProductPackageDetail;
import com.github.jinglongyang.aad.service.support.AadOAuth2Repository;

import static junit.framework.Assert.*;

public class BspServiceTemplateTest {
    private static final String TENANT_CONTEXT_ID = "graphDir1.onMicrosoft.com";
    private static final String CLIENT_ID = "118473c2-7619-46e3-a8e4-6da8d5f56e12";
    private static final String CLIENT_SECRET = "hOrJ0r0TZ4GQ3obp+vk3FZ7JBVP+TX353kNo6QwNq7Q=";
    public static final String APP_STORE_RESOURCE = "https://onestore.microsoft.com";

    private static final AuthenticationRequest request = new AuthenticationRequest(TENANT_CONTEXT_ID, CLIENT_ID, CLIENT_SECRET, APP_STORE_RESOURCE);

    @Test
    public void testGetInventory() throws Exception {
        BspServiceTemplate bspService = new BspServiceTemplate(new AadOAuth2Repository());
        InventoryEntries inventoryEntries = bspService.getInventory(request, LicenseType.OFFLINE, new Date(), null, null);
        assertNotNull(inventoryEntries);
        assertNull(inventoryEntries.getContinuationToken());
        List<InventoryEntry> entries = inventoryEntries.getInventoryEntries();
        assertEquals(1, entries.size());
        assertEquals(LicenseType.OFFLINE, entries.get(0).getLicenseType());
        assertEquals("9WZDNCRFJB5Q", entries.get(0).getProductKey().getProductId());
        assertEquals("0015", entries.get(0).getProductKey().getSkuId());
    }

    @Test
    public void testGetProductDetail() throws Exception {
        BspServiceTemplate bspService = new BspServiceTemplate(new AadOAuth2Repository());
        ProductDetail productDetail = bspService.getProductDetail(request, "9WZDNCRFJB5Q", "0015");
        assertNotNull(productDetail);
    }

    @Test
    public void testGetLocalizedProductDetail() throws Exception {
        BspServiceTemplate bspService = new BspServiceTemplate(new AadOAuth2Repository());
        LocalizedProductDetail localizedProductDetail = bspService.getLocalizedProductDetail(request, "9WZDNCRFJB5Q", "0015", "en-US");
        assertNotNull(localizedProductDetail);
    }


    @Test
    public void testGetOfflineLicense() throws Exception {
        BspServiceTemplate bspService = new BspServiceTemplate(new AadOAuth2Repository());
        OfflineLicense offlineLicense = bspService.getOfflineLicense(request, "9WZDNCRFJB5Q", "0015", "feb9764f-4e7e-2548-28cd-3681491ef81f");
        assertNotNull(offlineLicense);
    }

    @Test
    public void testGetProductPackageDetail() throws Exception {
        BspServiceTemplate bspService = new BspServiceTemplate(new AadOAuth2Repository());
        ProductPackageDetail productPackage = bspService.getProductPackage(request, "9WZDNCRFJB5Q", "0015");
        assertNotNull(productPackage);
    }
}