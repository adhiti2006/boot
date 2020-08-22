package com.ss.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    AuthenticationService authenticationService;

    @Test
    void whenValidHeader_isValidHeaders_shouldReturnTrue() {
        //given
        Map<String, List<String>> headerMap = new HashMap<>();
        headerMap.put("client-id", getHeaderValueList("test"));
        headerMap.put("client-secret", getHeaderValueList("test"));
        headerMap.put("client-name", getHeaderValueList("swagger"));
        //when
        boolean result = authenticationService.isValidHeaders(headerMap);
        //then
        assert result;
    }

    @Test
    void whenInValidHeader_isValidHeaders_shouldReturnFalse() {
        //given
        Map<String, List<String>> headerMap = new HashMap<>();
        headerMap.put("client-id", getHeaderValueList("test"));
        headerMap.put("client-secret", getHeaderValueList("test"));
        //when
        boolean result = authenticationService.isValidHeaders(headerMap);
        //then
        assert !result;
    }

    @Test
    void whenValidClient_isValidClient_ShouldReturnTrue() {
        //given
        Map<String, List<String>> headerMap = new HashMap<>();
        headerMap.put("client-name", getHeaderValueList("swagger"));
        //when
        boolean result = authenticationService.isValidClient(headerMap);
        //then
        assert result;
    }

    @Test
    void whenInValidClient_isValidClient_ShouldReturnFalse() {
        //given
        Map<String, List<String>> headerMap = new HashMap<>();
        headerMap.put("client-name", getHeaderValueList("automation"));
        //when
        boolean result = authenticationService.isValidClient(headerMap);
        //then
        assert !result;
    }

    @Test
    void whenNonSecureURI_isNonSecureURI_ShouldReturnTrue() {
        //given
        String requestURI = "/heartbeat";
        //when
        boolean result = authenticationService.isNonSecureURI(requestURI);
        //then
        assert result;
    }

    @Test
    void whenSecureURI_isNonSecureURI_ShouldReturnFalse() {
        //given
        String requestURI = "/test";
        //when
        boolean result = authenticationService.isNonSecureURI(requestURI);
        //then
        assert !result;
    }

    @Test
    void whenValidClientIdAndSecret_authenticate_shouldReturnTrue() {
        //given
        Map<String, List<String>> headerMap = new HashMap<>();
        headerMap.put("client-id", getHeaderValueList("uYDyUW/S1jCKIWX8ZsrjjTTqHdgAwwX5fQ00Lg9ACugWY0dS1JJf/4Mb7brSUUVl"));
        headerMap.put("client-secret", getHeaderValueList("C1cmXZs1+1jwRiBjCKD2a0javmov65I95AX8x5gOzdAxKkyn1m5XCjoppT5Ksk2xAC2jZ3ND5WMgUT4+LG3R8A=="));
        //when
        boolean result = authenticationService.authenticate(headerMap);
        //then
        assert result;
    }

    @Test
    void whenInValidClientIdAndSecret_authenticate_shouldReturnFalse(){
        //given
        Map<String, List<String>> headerMap = new HashMap<>();
        headerMap.put("client-id", getHeaderValueList("1uYDyUW/S1jCKIWX8ZsrjjTTqHdgAwwX5fQ00Lg9ACugWY0dS1JJf/4Mb7brSUUVl"));
        headerMap.put("client-secret", getHeaderValueList("C1cmXZs1+1jwRiBjCKD2a0javmov65I95AX8x5gOzdAxKkyn1m5XCjoppT5Ksk2xAC2jZ3ND5WMgUT4+LG3R8A=="));
        //when
        boolean result = authenticationService.authenticate(headerMap);
        //then
        assert !result;
    }

    //helper
    private List<String> getHeaderValueList(String headerValue) {
        List<String> clientIdValues = new ArrayList<>();
        clientIdValues.add(headerValue);
        return clientIdValues;
    }

}