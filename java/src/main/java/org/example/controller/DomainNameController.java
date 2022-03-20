/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.dto.UrlRequestDTO;
import org.example.dto.UrlResultDTO;
import org.example.manager.IDomainNameManager;
import org.example.validate.group.ShortUrl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 域名转换控制器
 *
 * @author xiaohongchao
 * @since 1.0
 */
@Api(value = "短域名服务")
@RestController
@RequestMapping("/short-domain-name")
public class DomainNameController {

    /**
     * 域名管理服务
     */
    @Resource
    private IDomainNameManager domainNameManager;

    /**
     * 短域名存储
     *
     * @param urlRequestDTO 长域名
     * @return 短域名
     */
    @ApiOperation(
            value = "短域名存储接口",
            notes = "接受长域名信息，返回短域名信息",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(
            value = "/storage",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UrlResultDTO> storageDomainName(@RequestBody @Validated
                                                                  UrlRequestDTO urlRequestDTO) {
        String shortUrl = domainNameManager.storageUrl(urlRequestDTO.getIp(), urlRequestDTO.getDomainName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UrlResultDTO.successWithData(shortUrl));
    }

    /**
     * 转换回长域名
     *
     * @param urlRequestDTO 短域名
     * @return 长域名
     */
    @ApiOperation(value = "短域名读取接口",
            notes = "接受短域名信息，返回长域名信息",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/restore",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UrlResultDTO> restoreDomainName(@RequestBody @Validated(ShortUrl.class)
                                                                  UrlRequestDTO urlRequestDTO) {
        String longUrl = domainNameManager.restoreUrl(urlRequestDTO.getDomainName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UrlResultDTO.successWithData(longUrl));
    }
}
