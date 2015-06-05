package com.github.jinglongyang.aad.domain;

public class AuthenticationRequest {
    private String tenantContextId;
    private String clientId;
    private String clientSecret;
    private String resource;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String tenantContextId, String clientId, String clientSecret, String resource) {
        this.tenantContextId = tenantContextId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.resource = resource;
    }

    public String getTenantContextId() {
        return tenantContextId;
    }

    public void setTenantContextId(String tenantContextId) {
        this.tenantContextId = tenantContextId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthenticationRequest)) return false;

        AuthenticationRequest that = (AuthenticationRequest) o;
        if (!tenantContextId.equals(that.tenantContextId)) return false;
        if (!clientId.equals(that.clientId)) return false;
        if (!clientSecret.equals(that.clientSecret)) return false;
        return !(resource != null ? !resource.equals(that.resource) : that.resource != null);
    }

    @Override
    public int hashCode() {
        return String.format("%s_%s_%s_%s", tenantContextId, clientId, clientSecret, resource).hashCode();
    }
}
