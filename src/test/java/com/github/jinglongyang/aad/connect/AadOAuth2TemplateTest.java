package com.github.jinglongyang.aad.connect;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jinglongyang.aad.domain.AccessGrant;

import static junit.framework.Assert.*;


public class AadOAuth2TemplateTest {
    private static final Logger logger = LoggerFactory.getLogger(AadOAuth2TemplateTest.class);
    private static final String TENANT_CONTEXT_ID = "graphDir1.onMicrosoft.com";
    private static final String CLIENT_ID = "118473c2-7619-46e3-a8e4-6da8d5f56e12";
    private static final String CLIENT_SECRET = "hOrJ0r0TZ4GQ3obp+vk3FZ7JBVP+TX353kNo6QwNq7Q=";
    private static final AadOAuth2Template TEMPLATE = new AadOAuth2Template(TENANT_CONTEXT_ID, CLIENT_ID, CLIENT_SECRET);

    @Test
    public void testAuthClientWithoutResource() {
        AccessGrant accessGrant = TEMPLATE.authenticateClient();
        assertCommonFields(accessGrant);
        assertEquals("00000002-0000-0000-c000-000000000000", accessGrant.getResource());
    }

    @Test
    public void testAuthClientWithResource() {
        AccessGrant accessGrant = TEMPLATE.authenticateClient("https://graph.windows.net");
        assertCommonFields(accessGrant);
        assertEquals("https://graph.windows.net", accessGrant.getResource());
    }

    private void assertCommonFields(AccessGrant accessGrant) {
        assertEquals("Bearer", accessGrant.getTokenType());
        assertTrue(accessGrant.getExpiresIn() <= 3600);
        assertNotNull(accessGrant.getAccessToken());
        assertNotNull(accessGrant.getExpiresOn());
        logger.info("Access token is [{}]", accessGrant.getAccessToken());
    }
}