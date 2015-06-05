package com.github.jinglongyang.aad.domain;

import java.util.List;

public class ContentDetail {
    private String contentId;
    private List<PackageContentInfo> packageContentInfo;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public List<PackageContentInfo> getPackageContentInfo() {
        return packageContentInfo;
    }

    public void setPackageContentInfo(List<PackageContentInfo> packageContentInfo) {
        this.packageContentInfo = packageContentInfo;
    }
}
