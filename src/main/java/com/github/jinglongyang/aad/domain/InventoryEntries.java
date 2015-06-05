package com.github.jinglongyang.aad.domain;

import java.util.List;

public class InventoryEntries {
    private List<InventoryEntry> inventoryEntries;
    private String continuationToken;

    public List<InventoryEntry> getInventoryEntries() {
        return inventoryEntries;
    }

    public void setInventoryEntries(List<InventoryEntry> inventoryEntries) {
        this.inventoryEntries = inventoryEntries;
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }
}
