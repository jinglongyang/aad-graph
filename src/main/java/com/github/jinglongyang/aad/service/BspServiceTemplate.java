package com.github.jinglongyang.aad.service;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jinglongyang.aad.domain.AuthenticationRequest;
import com.github.jinglongyang.aad.domain.InventoryEntries;
import com.github.jinglongyang.aad.domain.InventoryEntry;
import com.github.jinglongyang.aad.domain.LicenseType;
import com.github.jinglongyang.aad.domain.OfflineLicense;
import com.github.jinglongyang.aad.domain.ProductDetail;
import com.github.jinglongyang.aad.domain.ProductPackageDetail;
import com.github.jinglongyang.aad.repository.AccessTokenRepository;
import com.google.common.collect.Lists;

public class BspServiceTemplate {
    private static final Logger LOG = LoggerFactory.getLogger(BspServiceTemplate.class);
    private String url = "https://bspmts.mp.microsoft.com/vDraft1";
    private AccessTokenRepository accessTokenRepository;
    private RestTemplate restTemplate;

    public BspServiceTemplate(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
        restTemplate = createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        List<HttpMessageConverter<?>> converters = Lists.newArrayListWithExpectedSize(3);
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().featuresToDisable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES).build();
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        return new RestTemplate(converters);
    }

    public BspServiceTemplate(AccessTokenRepository accessTokenRepository, RestTemplate restTemplate) {
        this.accessTokenRepository = accessTokenRepository;
        this.restTemplate = restTemplate;
    }

    public InventoryEntries getInventory(AuthenticationRequest request, LicenseType licenseType, Date modifiedSince, String continuationToken, Integer maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .pathSegment("Inventory")
                .queryParam("licenseType", licenseType)
                .queryParam("continuationToken", continuationToken)
                .queryParam("maxResults", maxResults);
        if (modifiedSince != null) {
            builder.queryParam("modifiedSince", new SimpleDateFormat(InventoryEntry.DATE_PATTERN).format(modifiedSince));
        }
        return getForObject(request, builder.build().toUri(), InventoryEntries.class);
    }

    public ProductDetail getProductDetail(AuthenticationRequest request, String productId, String skuId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("Products")
                .pathSegment(productId)
                .pathSegment(skuId)
                .build()
                .toUri();
        return getForObject(request, uri, ProductDetail.class);
    }

    public OfflineLicense getOfflineLicense(AuthenticationRequest request, String productId, String skuId, String contentId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("Products")
                .pathSegment(productId)
                .pathSegment(skuId)
                .pathSegment("OfflineLicense")
                .pathSegment(contentId)
                .build()
                .toUri();
        return postForObject(request, uri, OfflineLicense.class);
    }

    public ProductPackageDetail getProductPackage(AuthenticationRequest request, String productId, String skuId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("Products")
                .pathSegment(productId)
                .pathSegment(skuId)
                .pathSegment("Packages")
                .build()
                .toUri();
        return getForObject(request, uri, ProductPackageDetail.class);
    }

    private <T> T getForObject(AuthenticationRequest request, URI uri, Class<T> responseType) throws RestClientException {
        return execute(request, uri, HttpMethod.GET, responseType);
    }

    private <T> T postForObject(AuthenticationRequest request, URI uri, Class<T> responseType) throws RestClientException {
        return execute(request, uri, HttpMethod.POST, responseType);
    }

    public <T> T execute(AuthenticationRequest request, URI uri, HttpMethod method, Class<T> responseType) throws RestClientException {
        LOG.debug("The parameter is {}", uri);
        RequestCallback requestCallback = new AuthorizationHeaderRequestCallback(accessTokenRepository, request);
        HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<>(responseType, getMessageConverters());
        return restTemplate.execute(uri, method, requestCallback, responseExtractor);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        return restTemplate.getMessageConverters();
    }

    public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
        restTemplate.setRequestFactory(requestFactory);
    }

    private static class AuthorizationHeaderRequestCallback implements RequestCallback {
        private AccessTokenRepository accessTokenRepository;
        private AuthenticationRequest authenticationRequest;

        public AuthorizationHeaderRequestCallback(AccessTokenRepository accessTokenRepository, AuthenticationRequest authenticationRequest) {
            this.accessTokenRepository = accessTokenRepository;
            this.authenticationRequest = authenticationRequest;
        }

        @Override
        public void doWithRequest(ClientHttpRequest request) throws IOException {
            String token = accessTokenRepository.acquireToken(authenticationRequest);
            request.getHeaders().add("Authorization", String.format("Bearer %s", token));
        }
    }
}
