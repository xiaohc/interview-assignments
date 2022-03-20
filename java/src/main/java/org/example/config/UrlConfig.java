/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * URL 配置
 *
 * @author xiaohongchao
 * @since 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "url")
public class UrlConfig {

    /**
     * 服务
     */
    private String server;
}
