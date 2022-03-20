/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.controller;

import org.example.test.util.FileUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * 测试control接口
 *
 * @author xiaohongchao
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class DomainNameControllerTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private DomainNameController domainNameController;

    /**
     * 日志检查用
     */
    private static final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(output));
    }

    @Test
    void testDomainNameStorageAndRestore() throws Exception {
        // 保存长域名，返回短域名
        String storageRequestJson = FileUtils.readString("json/storage_request.json");

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/short-domain-name/storage")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(storageRequestJson);

        MockMvcBuilders.standaloneSetup(this.domainNameController)
                .build()
                .perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("成功"));
        // pitest not supported
//                .andExpect(MockMvcResultMatchers.jsonPath("$.domainName").value("https://t.hk.uy/AADAqArU"));

        // 检查记录了请求和应答日志
        assertThat(output.toString(), Matchers.containsString("(doBefore)"));
        assertThat(output.toString(), Matchers.containsString("(doAfterReturning)"));

        // 从短域名恢复为长域名
        String restoreRequestJson = FileUtils.readString("json/restore_request.json");

        MockHttpServletRequestBuilder restoreRequestBuilder =
                MockMvcRequestBuilders.post("/short-domain-name/restore")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(restoreRequestJson);

        MockMvcBuilders.standaloneSetup(this.domainNameController)
                .build()
                .perform(restoreRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("成功"));
        // pitest not supported
//                .andExpect(MockMvcResultMatchers.jsonPath("$.domainName").value("https://www.cnblogs.com/shimh/p/6093229.html"));

    }
}
