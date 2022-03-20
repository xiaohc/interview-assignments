/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 测试IP转换
 *
 * @author xiaohongchao
 * @since 1.0
 */
class IpConvertUtilsTest {
    @Test
    void testIpToInt() {
        assertEquals(2130706433, IpConvertUtils.ipToInt("127.0.0.1"));
        assertEquals(-1, IpConvertUtils.ipToInt("255.255.255.255"));
        assertEquals(-1062731419, IpConvertUtils.ipToInt("192.168.1.101"));
        assertEquals(-1940754172, IpConvertUtils.ipToInt("140.82.113.4"));
        assertEquals(1714839137, IpConvertUtils.ipToInt("102.54.94.97"));

        assertThrows(IllegalArgumentException.class, () -> IpConvertUtils.ipToInt(null));
        assertThrows(IllegalArgumentException.class, () -> IpConvertUtils.ipToInt(""));
        assertThrows(IllegalArgumentException.class, () -> IpConvertUtils.ipToInt("102.54.94"));
    }

    @Test
    void testIntToIP() {
        assertEquals("127.0.0.1", IpConvertUtils.intToIp(2130706433));
        assertEquals("255.255.255.255", IpConvertUtils.intToIp(-1));
        assertEquals("192.168.1.101", IpConvertUtils.intToIp(-1062731419));
        assertEquals("140.82.113.4", IpConvertUtils.intToIp(-1940754172));
        assertEquals("102.54.94.97", IpConvertUtils.intToIp(1714839137));
    }
}

