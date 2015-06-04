package com.github.jinglongyang.aad.connect;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.github.jinglongyang.aad.domain.AccessGrant;
import com.google.common.collect.Lists;

public class AadOAuth2Template {
    private static final String TOKEN_URL = "https://login.windows.net/%s/oauth2/token?api-version=%s";
    private final String clientId;
    private final String clientSecret;
    private final String accessTokenUrl;
    private RestTemplate restTemplate;

    public AadOAuth2Template(String tenantContextId, String clientId, String clientSecret) {
        this(tenantContextId, clientId, clientSecret, "1.5");
    }

    public AadOAuth2Template(String tenantContextId, String clientId, String clientSecret, String version) {
        this.accessTokenUrl = String.format(TOKEN_URL, tenantContextId, version);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public AccessGrant authenticateClient() {
        return authenticateClient(null);
    }

    public AccessGrant authenticateClient(String resource) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("client_id", clientId);
        params.set("client_secret", clientSecret);
        params.set("grant_type", "client_credentials");
        if (resource != null) {
            params.set("resource", resource);
        }
        return getRestTemplate().postForObject(accessTokenUrl, params, AccessGrant.class);
    }

    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
        List<HttpMessageConverter<?>> converters = Lists.newArrayListWithExpectedSize(3);
        converters.add(new FormHttpMessageConverter());
        converters.add(new FormMapHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    protected RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = createRestTemplate();
        }
        return restTemplate;
    }

    public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
        Assert.notNull(requestFactory, "The requestFactory property cannot be null");
        getRestTemplate().setRequestFactory(requestFactory);
    }

    private static class FormMapHttpMessageConverter implements HttpMessageConverter<Map<String, String>> {
        private final FormHttpMessageConverter delegate;

        public FormMapHttpMessageConverter() {
            delegate = new FormHttpMessageConverter();
        }

        public boolean canRead(Class<?> clazz, MediaType mediaType) {
            if (!Map.class.isAssignableFrom(clazz)) {
                return false;
            }
            if (mediaType == null) {
                return true;
            }
            for (MediaType supportedMediaType : getSupportedMediaTypes()) {
                // we can't read multipart
                if (!supportedMediaType.equals(MediaType.MULTIPART_FORM_DATA) &&
                        supportedMediaType.includes(mediaType)) {
                    return true;
                }
            }
            return false;
        }

        public boolean canWrite(Class<?> clazz, MediaType mediaType) {
            if (!Map.class.isAssignableFrom(clazz)) {
                return false;
            }
            if (mediaType == null || MediaType.ALL.equals(mediaType)) {
                return true;
            }
            for (MediaType supportedMediaType : getSupportedMediaTypes()) {
                if (supportedMediaType.isCompatibleWith(mediaType)) {
                    return true;
                }
            }
            return false;
        }

        public List<MediaType> getSupportedMediaTypes() {
            return delegate.getSupportedMediaTypes();
        }

        public Map<String, String> read(Class<? extends Map<String, String>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
            LinkedMultiValueMap<String, String> lmvm = new LinkedMultiValueMap<>();
            @SuppressWarnings("unchecked")
            Class<LinkedMultiValueMap<String, String>> mvmClazz = (Class<LinkedMultiValueMap<String, String>>) lmvm.getClass();
            MultiValueMap<String, String> mvm = delegate.read(mvmClazz, inputMessage);

            return mvm.toSingleValueMap();
        }

        public void write(Map<String, String> t, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
            MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
            mvm.setAll(t);
            delegate.write(mvm, contentType, outputMessage);
        }
    }
}
