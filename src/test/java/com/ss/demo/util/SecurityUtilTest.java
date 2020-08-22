package com.ss.demo.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecurityUtilTest {
    SecurityUtil securityUtil;

    @BeforeEach
    void setUp() {
        securityUtil = new SecurityUtil();
    }

    @Test
    void encryptText() throws Exception {
        //given
        String text = "testclientid";
        //when
        String result = securityUtil.encrypt(text);
        //then
        assert StringUtils.isNotBlank(result);
    }

}
