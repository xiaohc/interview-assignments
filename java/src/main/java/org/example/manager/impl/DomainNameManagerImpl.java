/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.manager.impl;

import org.apache.commons.lang3.StringUtils;
import org.example.config.UrlConfig;
import org.example.manager.IDomainNameManager;
import org.example.util.IpConvertUtils;
import org.example.util.UrlEncodeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 域名管理
 * 实现类
 *
 * @author xiaohongchao
 * @since 1.0
 */
@Service
public class DomainNameManagerImpl implements IDomainNameManager {
    /**
     * 定义 map 初始化容量
     */
    private static final Integer MAP_INITIAL_CAPACITY = 65536;

    /**
     * 存储 URL 对应关系
     * key 为短域名特征码， value 为长域名
     * 简单设计：映射数据存储在内存中，不考虑落盘保存
     */
    private static final Map<String, String> URL_MAP = new ConcurrentHashMap<>(MAP_INITIAL_CAPACITY);

    /**
     * IP 请求次数计数,用于长域名转换短域名
     * key 为IP整数形式
     * value 为对应IP的请求次数
     * 简单设计：仅支持IPv4地址
     */
    private static final Map<Integer, AtomicInteger> IP_COUNT_MAP = new ConcurrentHashMap<>(MAP_INITIAL_CAPACITY);

    /**
     * URL配置
     */
    @Resource
    private UrlConfig urlConfig;

    @Override
    public String storageUrl(String ip, String longUrl) {
        String shortUrlCode = generateShortUrlCode(ip);

        URL_MAP.computeIfAbsent(shortUrlCode, k -> longUrl);

        return urlConfig.getServer() + shortUrlCode;
    }

    /**
     * 生成 Url 特征码
     * <p>
     * 简单设计：仅支持IPv4地址
     * shortUrl 8 个字符，用 48 bit 表示
     * 低32位用于保存IPv4地址，高16位用于存放对应IP的请求次数（即单IP最多记录65535个URL转换内容）
     *
     * @param ip IP地址
     * @return shortUrl
     */
    private String generateShortUrlCode(String ip) {
        int intIp = IpConvertUtils.ipToInt(ip);

        AtomicInteger atomicSeq = IP_COUNT_MAP.computeIfAbsent(intIp, k -> new AtomicInteger());
        int intSeq = atomicSeq.addAndGet(1);

        return UrlEncodeUtils.encode(intSeq, intIp);
    }

    @Override
    public String restoreUrl(String shortUrl) {
        String shortUrlCode = StringUtils.remove(shortUrl, urlConfig.getServer());
        return URL_MAP.getOrDefault(shortUrlCode, "短域名无效或已过期，请重新输入");
    }
}
