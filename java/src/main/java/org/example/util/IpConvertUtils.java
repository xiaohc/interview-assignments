/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.util;

import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IP转换工具类
 * 十进制整数形式，预127.0.0.1形式的ip地址相互转换
 *
 * @author xiaohongchao
 * @since 1.0
 */
public final class IpConvertUtils {

    /**
     * 常量字符: .
     */
    public static final String DOT_STR = ".";

    /**
     * . 字符正则表达式
     */
    public static final String DOT_REGEX = "\\.";

    /**
     * 私有化工具类构造函数
     */
    private IpConvertUtils() {
    }

    /**
     * 将127.0.0.1形式的IP地址转换成十进制整数
     *
     * @param strIp IP字符串形式
     * @return IP整数形式
     */
    public static int ipToInt(String strIp) {
        Assert.hasText(strIp, "Must be a valid IPv4 address.");
        List<Integer> ipList = Arrays.stream(strIp.split(DOT_REGEX))
                .map(Integer::parseUnsignedInt)
                .collect(Collectors.toList());

        Assert.isTrue(ipList.size() == 4, "Must be a valid and complete IP address");

        return (ipList.get(0) << 24) + (ipList.get(1) << 16) + (ipList.get(2) << 8) + ipList.get(3);
    }

    /**
     * 将十进制整数形式转换成127.0.0.1形式的ip地址
     *
     * @param intIp IP整数形式
     * @return IP字符串形式
     */
    public static String intToIp(int intIp) {
        return (intIp >>> 24) + DOT_STR
                //将高8位置0，然后右移16位
                + ((intIp & 0x00FFFFFF) >>> 16) + DOT_STR
                //将高16位置0，然后右移8位
                + ((intIp & 0x0000FFFF) >>> 8) + DOT_STR
                //将高24位置0
                + (intIp & 0x000000FF);
    }
}
