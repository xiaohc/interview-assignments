/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Swagger配置
 *
 * @author xiaohongchao
 * @since 1.0
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    /**
     * swagger-ui 3.0.0版本访问网页如下
     * http://localhost:8080/swagger-ui/index.html
     *
     * @return swagger配置内容
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example.controller"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDateTime.class, String.class)
                .protocols(new LinkedHashSet<>(Arrays.asList("https", "http")))
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .apiInfo(apiInfo());
    }

    /**
     * @return api的元信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("短域名服务")
                .description("短域名服务中心 API 1.0 操作文档")
                .termsOfServiceUrl("https://github.com/xiaohc/short-domain-name-service")
                .version("1.0")
                .contact(new Contact("肖红超", "https://github.com/xiaohc", "xiaohongchao@hotmail.com"))
                .build();
    }

    /**
     * @return 设置的安全模式
     */
    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("Authorization", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    /**
     * @return 设置的安全上下文
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference("Authorization",
                                new AuthorizationScope[]{new AuthorizationScope("global", "")})))
                        .build()
        );
    }
}
