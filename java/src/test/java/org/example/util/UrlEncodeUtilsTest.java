/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 测试 URL短域名加解密工具类
 *
 * @author xiaohongchao
 * @since 1.0
 */
class UrlEncodeUtilsTest {
    @Test
    void testEncode() {
        assertEquals("AAF/AAAB", UrlEncodeUtils.encode(1, IpConvertUtils.ipToInt("127.0.0.1")));
        assertEquals("//7/////", UrlEncodeUtils.encode(-1, IpConvertUtils.ipToInt("255.255.255.255")));
        assertEquals("///AqAFl", UrlEncodeUtils.encode(0, IpConvertUtils.ipToInt("192.168.1.101")));
        assertEquals("Jy+MUnEE", UrlEncodeUtils.encode(10032, IpConvertUtils.ipToInt("140.82.113.4")));
        assertEquals("A+dmNl5h", UrlEncodeUtils.encode(999, IpConvertUtils.ipToInt("102.54.94.97")));
    }

    @Test
    void testDecodeIp() {
        assertEquals(IpConvertUtils.ipToInt("127.0.0.1"), UrlEncodeUtils.decodeIp("AAF/AAAB"));
        assertEquals(IpConvertUtils.ipToInt("255.255.255.255"), UrlEncodeUtils.decodeIp("//7/////"));
        assertEquals(IpConvertUtils.ipToInt("192.168.1.101"), UrlEncodeUtils.decodeIp("///AqAFl"));
        assertEquals(IpConvertUtils.ipToInt("140.82.113.4"), UrlEncodeUtils.decodeIp("Jy+MUnEE"));
        assertEquals(IpConvertUtils.ipToInt("102.54.94.97"), UrlEncodeUtils.decodeIp("A+dmNl5h"));

        assertThrows(IllegalArgumentException.class, () -> UrlEncodeUtils.decodeIp(null));
        assertThrows(IllegalArgumentException.class, () -> UrlEncodeUtils.decodeIp(""));
        assertThrows(IllegalArgumentException.class, () -> UrlEncodeUtils.decodeIp("2hytQxrUAA"));
    }

    @Test
    void testDecodeSeq() {
        assertEquals(1, UrlEncodeUtils.decodeSeq("AAF/AAAB"));
        assertEquals(65534, UrlEncodeUtils.decodeSeq("//7/////"));
        assertEquals(65535, UrlEncodeUtils.decodeSeq("///AqAFl"));
        assertEquals(10031, UrlEncodeUtils.decodeSeq("Jy+MUnEE"));
        assertEquals(999, UrlEncodeUtils.decodeSeq("A+dmNl5h"));

        assertThrows(IllegalArgumentException.class, () -> UrlEncodeUtils.decodeSeq(""));
        assertThrows(IllegalArgumentException.class, () -> UrlEncodeUtils.decodeSeq(null));
    }
}

