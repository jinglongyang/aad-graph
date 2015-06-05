package com.github.jinglongyang.aad.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class DefaultBspService implements BspService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultBspService.class);

    private String url = "https://bspmts.mp.microsoft.com/vDraft1";
    private AccessTokenRepository accessTokenRepository;
    private RestTemplate restTemplate;

    public DefaultBspService(AccessTokenRepository accessTokenRepository) {
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

    public DefaultBspService(AccessTokenRepository accessTokenRepository, RestTemplate restTemplate) {
        this.accessTokenRepository = accessTokenRepository;
        this.restTemplate = restTemplate;
    }

    public InventoryEntries getInventory(AuthenticationRequest request, LicenseType licenseType, Date modifiedSince, String continuationToken, Integer maxResults) {
        Map<String, String> params = new HashMap<>();
        if (licenseType != null) {
            params.put("licenseType", licenseType.getValue());
        }
        if (continuationToken != null) {
            params.put("continuationToken", continuationToken);
        }
        if (modifiedSince != null) {
            params.put("modifiedSince", new SimpleDateFormat(InventoryEntry.DATE_PATTERN).format(modifiedSince));
        }
        if (maxResults != null) {
            params.put("maxResults", maxResults.toString());
        }
        logger.debug("The parameter is {}", params);
        return getForObject(request, String.format("%s/Inventory", url), InventoryEntries.class, params);
    }

    @Override
    public ProductDetail getProductDetail(AuthenticationRequest request, String productId, String skuId) {
        return getForObject(request, String.format("%s/Products/%s/%s", url, productId, skuId), ProductDetail.class);
    }

    @Override
    public OfflineLicense getOfflineLicense(AuthenticationRequest request, String productId, String skuId, String contentId) {
        return postForObject(request, String.format("%s/Products/%s/%s/OfflineLicense/%s", url, productId, skuId, contentId), OfflineLicense.class);
    }

    @Override
    public ProductPackageDetail getProductPackage(AuthenticationRequest request, String productId, String skuId) {
        return getForObject(request, String.format("%s/Products/%s/%s/Packages", url, productId, skuId), ProductPackageDetail.class);
    }

    private <T> T getForObject(AuthenticationRequest request, String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        return execute(request, url, HttpMethod.GET, responseType, urlVariables);
    }

    private <T> T postForObject(AuthenticationRequest request, String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        return execute(request, url, HttpMethod.POST, responseType, urlVariables);
    }

    public <T> T execute(AuthenticationRequest request, String url, HttpMethod method, Class<T> responseType, Object... urlVariables) throws RestClientException {
        RequestCallback requestCallback = new AuthorizationHeaderRequestCallback(accessTokenRepository, request);
        HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<>(responseType, getMessageConverters());

        return restTemplate.execute(url, method, requestCallback, responseExtractor, urlVariables);
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
