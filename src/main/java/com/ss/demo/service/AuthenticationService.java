package com.ss.demo.service;

import com.ss.demo.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class AuthenticationService {

    private static final String HEADER_CLIENT_NAME = "CLIENT-NAME";
    private static final String HEADER_CLIENT_ID = "CLIENT-ID";
    private static final String HEADER_CLIENT_SECRET = "CLIENT-SECRET";

    @Value("${demo.client.id}")
    private String clientId;

    @Value("${demo.client.secret}")
    private String clientSecret;

    @Value("${demo.client.names}")
    private List<String> clientNames;

    @Value("${demo.non.secure.uris}")
    private List<String> nonSecureURIs;

    @Autowired
    SecurityUtil securityUtil;

    public boolean authenticate(Map<String, List<String>> headers) {
        if (!StringUtils.isEmpty(clientId) && !StringUtils.isEmpty(clientSecret)) {
            try {
                return clientId.equals(securityUtil.decrypt(getHeaderValue(headers, HEADER_CLIENT_ID))) && clientSecret.equals(securityUtil.decrypt(getHeaderValue(headers, HEADER_CLIENT_SECRET)));
            } catch (Exception exception) {
                // log the exception
            }
        }
        return false;
    }

    public boolean isValidClient(Map<String, List<String>> headerMap) {
        return clientNames.contains(getHeaderValue(headerMap, HEADER_CLIENT_NAME));
    }

    public boolean isValidHeaders(Map<String, List<String>> headerMap) {
        return !StringUtils.isEmpty(getHeaderValue(headerMap, HEADER_CLIENT_ID))
                && !StringUtils.isEmpty(getHeaderValue(headerMap, HEADER_CLIENT_SECRET))
                && !StringUtils.isEmpty(getHeaderValue(headerMap, HEADER_CLIENT_NAME));
    }

    String getHeaderValue(Map<String, List<String>> headers, String key) {
        String headerValue = "";
        if (!CollectionUtils.isEmpty(headers.get(key.toLowerCase()))) {
            headerValue = headers.get(key.toLowerCase()).get(0);
        }
        return headerValue;
    }

    public boolean isNonSecureURI(String requestURI) {
        return nonSecureURIs.contains(requestURI);
    }
}
