package com.github.jinglongyang.aad.service.support;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jinglongyang.aad.domain.AccessGrant;
import com.github.jinglongyang.aad.domain.AuthenticationRequest;
import com.github.jinglongyang.aad.repository.AccessTokenRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

public class AadOAuth2Repository implements AccessTokenRepository {
    private static final Logger LOG = LoggerFactory.getLogger(AadOAuth2Repository.class);

    private static final String AAD_TOKEN_URL = "https://login.windows.net/%s/oauth2/token?api-version=%s";
    private static final int EXPIRE_IN = 3500;

    private final String version;
    private final RestTemplate restTemplate;

    private LoadingCache<AuthenticationRequest, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(EXPIRE_IN, TimeUnit.SECONDS)
            .build(new CacheLoader<AuthenticationRequest, String>() {
                @Override
                public String load(AuthenticationRequest request) throws Exception {
                    return authenticateClient(request).getAccessToken();
                }
            });

    public AadOAuth2Repository(String version) {
        this.version = version;
        restTemplate = createRestTemplate();
    }

    public AadOAuth2Repository() {
        this("1.5");
    }

    public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
        restTemplate.setRequestFactory(requestFactory);
    }

    @Override
    public String acquireToken(AuthenticationRequest request) {
        String token = cache.getUnchecked(request);
        LOG.debug("The BSP access token is [{}]", token);
        return token;
    }

    private AccessGrant authenticateClient(AuthenticationRequest request) {
        String accessTokenUrl = String.format(AAD_TOKEN_URL, request.getTenantContextId(), version);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("client_id", request.getClientId());
        params.set("client_secret", request.getClientSecret());
        params.set("grant_type", "client_credentials");
        if (request.getResource() != null) {
            params.set("resource", request.getResource());
        }
        //TODO error handling
        return restTemplate.postForObject(accessTokenUrl, params, AccessGrant.class);
    }

    private RestTemplate createRestTemplate() {
        List<HttpMessageConverter<?>> converters = Lists.newArrayListWithExpectedSize(2);
        converters.add(new FormHttpMessageConverter());
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().featuresToDisable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES).build();
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        return new RestTemplate(converters);
    }
}
