/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.manager;

/**
 * 域名管理
 * 接口
 *
 * @author xiaohongchao
 * @since 1.0
 */
public interface IDomainNameManager {
    /**
     * 保存为短域名
     *
     * @param ip      ipv4地址
     * @param longUrl 长域名
     * @return shortUrl短域名
     */
    String storageUrl(String ip, String longUrl);

    /**
     * 恢复为长域名
     *
     * @param shortUrl 短域名
     * @return longUrl长域名
     */
    String restoreUrl(String shortUrl);
}
