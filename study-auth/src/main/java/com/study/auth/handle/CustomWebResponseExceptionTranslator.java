package com.study.auth.handle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

@Component("customWebResponseExceptionTranslator")
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<Exception> translate(Exception e) throws Exception {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new CustomOauthException(e.getMessage()));
    }

}
