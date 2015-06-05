package com.github.jinglongyang.aad.repository;

import com.github.jinglongyang.aad.domain.AuthenticationRequest;

public interface AccessTokenRepository {
    String acquireToken(AuthenticationRequest request);
}
