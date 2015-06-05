package com.github.jinglongyang.aad.domain;

import java.util.List;

public class ProductPackage {
    private String packageId;
    private PackageLocation location;
    private long fileSize;
    private String contentId;
    private String packageFullName;
    private String packageIdentityName;
    private List<ProductPackageArchitecture> architectures;
    private String packageFormat;
    private List<ProductPlatform> platforms;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public PackageLocation getLocation() {
        return location;
    }

    public void setLocation(PackageLocation location) {
        this.location = location;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getPackageFullName() {
        return packageFullName;
    }

    public void setPackageFullName(String packageFullName) {
        this.packageFullName = packageFullName;
    }

    public String getPackageIdentityName() {
        return packageIdentityName;
    }

    public void setPackageIdentityName(String packageIdentityName) {
        this.packageIdentityName = packageIdentityName;
    }

    public List<ProductPackageArchitecture> getArchitectures() {
        return architectures;
    }

    public void setArchitectures(List<ProductPackageArchitecture> architectures) {
        this.architectures = architectures;
    }

    public String getPackageFormat() {
        return packageFormat;
    }

    public void setPackageFormat(String packageFormat) {
        this.packageFormat = packageFormat;
    }

    public List<ProductPlatform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<ProductPlatform> platforms) {
        this.platforms = platforms;
    }
}
