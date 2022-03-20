/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.controller;

import org.example.test.util.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 测试 通用异常处理
 *
 * @author xiaohongchao
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RequestExceptionsTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void testHandlerArgumentNotValidException() throws Exception {
        // call storage domain name
        String storageRequestJson = FileUtils.readString("json/invalid_storage_request.json");

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/short-domain-name/storage")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(storageRequestJson);

        mvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("[请求IP必须是有效的IPv4地址]"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.domainName").isEmpty());
    }
}