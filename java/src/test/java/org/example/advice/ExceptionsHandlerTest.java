/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.advice;

import org.example.controller.DomainNameController;
import org.example.manager.IDomainNameManager;
import org.example.test.util.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;

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
class ExceptionsHandlerTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
//    @InjectMocks
    private DomainNameController domainNameController;

    @Mock
    private IDomainNameManager domainNameManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(domainNameController, "domainNameManager", domainNameManager);
    }

    @Test
    void testHandlerUnknownException() throws Exception {
        //  call mock object returns an exception
        Mockito.doAnswer(
                invocation -> {
                    throw new Exception("foo");
                })
                .when(domainNameManager)
                .storageUrl(any(), any());

        // call storage domain name
        String storageRequestJson = FileUtils.readString("json/storage_request.json");

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/short-domain-name/storage")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(storageRequestJson);

        mvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.domainName").isEmpty());
    }

}