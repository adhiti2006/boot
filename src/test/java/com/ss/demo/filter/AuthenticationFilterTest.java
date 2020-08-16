package com.ss.demo.filter;

import com.ss.demo.exception.NotAuthorizedException;
import com.ss.demo.service.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    AuthenticationFilter authenticationFilter;

    @Mock
    AuthenticationService mockAuthenticationService;

    MockHttpServletRequest mockHttpServletRequest;

    MockHttpServletResponse mockHttpServletResponse;

    @BeforeEach
    void setUp() {
        authenticationFilter = new AuthenticationFilter();
        authenticationFilter.authenticationService = mockAuthenticationService;
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletResponse = new MockHttpServletResponse();
    }

    @Test
    void noSecureURI_preHandle() throws Exception {
        //given
        mockHttpServletRequest.addHeader("client-id", "swagger");
        //when
        when(mockAuthenticationService.isNonSecureURI(anyString())).thenReturn(true);
        boolean result = authenticationFilter.preHandle(mockHttpServletRequest, mockHttpServletResponse, new Object());
        //then
        assert result;
    }


    @Test
    void requiredClientHeadersSetDuring_preHandle() throws Exception {
        //given
        mockHttpServletRequest.addHeader("client-id", "swagger");
        //when
        when(mockAuthenticationService.isNonSecureURI(anyString())).thenReturn(false);
        when(mockAuthenticationService.isValidHeaders(anyMap())).thenReturn(true);
        when(mockAuthenticationService.isValidClient(anyMap())).thenReturn(true);
        when(mockAuthenticationService.authenticate(anyMap())).thenReturn(true);
        boolean result = authenticationFilter.preHandle(mockHttpServletRequest, mockHttpServletResponse, new Object());
        //then
        assert result;
    }

    @Test()
    void requiredClientHeadersNotSetDuring_preHandle() throws NotAuthorizedException {
        //given
        mockHttpServletRequest.addHeader("client-id", "swagger");
        //when
        when(mockAuthenticationService.isNonSecureURI(anyString())).thenReturn(false);
        when(mockAuthenticationService.isValidHeaders(anyMap())).thenReturn(false);
        Exception exception = Assertions.assertThrows(NotAuthorizedException.class, () -> authenticationFilter.preHandle(mockHttpServletRequest, mockHttpServletResponse, new Object()));
        //then
        assert exception.getMessage().equals("Required client headers not set");
    }

    @Test()
    void requiredClientNameNotValidDuring_preHandle() throws NotAuthorizedException {
        //given
        mockHttpServletRequest.addHeader("client-id", "swagger");
        //when
        when(mockAuthenticationService.isNonSecureURI(anyString())).thenReturn(false);
        when(mockAuthenticationService.isValidHeaders(anyMap())).thenReturn(true);
        when(mockAuthenticationService.isValidClient(anyMap())).thenReturn(false);
        Exception exception = Assertions.assertThrows(NotAuthorizedException.class, () -> authenticationFilter.preHandle(mockHttpServletRequest, mockHttpServletResponse, new Object()));
        //then
        assert exception.getMessage().equals("User does not have access to this resource");
    }

    @Test()
    void inValidClientIdAndSecretDuring_preHandle() throws NotAuthorizedException {
        //given
        mockHttpServletRequest.addHeader("client-id", "swagger");
        //when
        when(mockAuthenticationService.isNonSecureURI(anyString())).thenReturn(false);
        when(mockAuthenticationService.isValidHeaders(anyMap())).thenReturn(true);
        when(mockAuthenticationService.isValidClient(anyMap())).thenReturn(true);
        when(mockAuthenticationService.authenticate(anyMap())).thenReturn(false);
        Exception exception = Assertions.assertThrows(NotAuthorizedException.class, () -> authenticationFilter.preHandle(mockHttpServletRequest, mockHttpServletResponse, new Object()));
        //then
        assert exception.getMessage().equals("User does not have permission to access this resource");
    }

}