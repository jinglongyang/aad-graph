package com.github.jinglongyang.aad.service.support;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jinglongyang.aad.domain.AuthenticationRequest;

import static junit.framework.Assert.*;

public class AadOAuth2RepositoryTest {
    public static final String AAD_RESOURCE = "https://graph.windows.net";
    private static final Logger logger = LoggerFactory.getLogger(AadOAuth2RepositoryTest.class);
    private static final String TENANT_CONTEXT_ID = "graphDir1.onMicrosoft.com";
    private static final String CLIENT_ID = "118473c2-7619-46e3-a8e4-6da8d5f56e12";
    private static final String CLIENT_SECRET = "hOrJ0r0TZ4GQ3obp+vk3FZ7JBVP+TX353kNo6QwNq7Q=";
    private static final AuthenticationRequest request = new AuthenticationRequest(TENANT_CONTEXT_ID, CLIENT_ID, CLIENT_SECRET, AAD_RESOURCE);
    private static final AadOAuth2Repository TEMPLATE = new AadOAuth2Repository("1.5");

    @Test
    public void testAuthClient() {
        String token = TEMPLATE.acquireToken(request);
        logger.debug("Access token is [{}]", token);
        assertNotNull(token);
    }

    @Test
    public void testAuthClientWithSameRequest() {
        String token1 = TEMPLATE.acquireToken(request);
        assertNotNull(token1);
        String token2 = TEMPLATE.acquireToken(request);
        assertSame(token1, token2);
    }
}