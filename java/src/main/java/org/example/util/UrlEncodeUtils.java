/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.util;

import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

/**
 * URL短域名加解密工具类
 *
 * @author xiaohongchao
 * @since 1.0
 */
public final class UrlEncodeUtils {

    /**
     * 转换基数
     */
    private static final int RADIX = 6;

    /**
     * 常量8，1 Byte 有 8 Bit，long有 8 Byte
     */
    private static final int EIGHT = 8;

    /**
     * 私有化工具类构造函数
     */
    private UrlEncodeUtils() {
    }

    /**
     * 加密为URL短域名
     *
     * @param intSeq Seq整数形式
     * @param intIp  IP整数形式
     * @return URL短域名
     */
    public static String encode(int intSeq, int intIp) {
        long longUrl = ((long) intSeq << 32) + intIp;
        return Base64Utils.encodeToString(long2ByteArray(longUrl));
    }

    /**
     * @param shortUrl URL短域名
     * @return IP整数形式
     */
    public static int decodeIp(String shortUrl) {
        Assert.hasText(shortUrl, "Invalid input argument to decodeIp function.");
        byte[] decode = Base64Utils.decodeFromString(shortUrl);
        return (int) byteArray2Long(decode, decode.length);
    }

    /**
     * @param shortUrl URL短域名
     * @return Seq整数形式
     */
    public static int decodeSeq(String shortUrl) {
        Assert.hasText(shortUrl, "Invalid input argument to decodeSeq function.");
        byte[] decode = Base64Utils.decodeFromString(shortUrl);
        long longUrl = byteArray2Long(decode, 2);
        return (int) (longUrl);
    }

    /**
     * long to byte[]
     *
     * @param aLong long值
     * @return byte[] 长度为6的byte的数组
     */
    private static byte[] long2ByteArray(long aLong) {
        byte[] ret = new byte[RADIX];

        for (int i = 0; i < RADIX; i++) {
            int offset = (ret.length - 1 - i) * EIGHT;
            ret[i] = (byte) ((aLong >>> offset) & 0xff);
        }

        return ret;
    }

    /**
     * 将一个长度不超过8的byte的数组转换成64位的long
     *
     * @param buf 一个长度不超过8的byte的数组
     * @param pos 截止为主
     * @return 转换后的long值
     */
    private static long byteArray2Long(byte[] buf, int pos) {
        Assert.isTrue(buf.length <= RADIX, "Invalid input argument to byteArray2Long function.");

        long ret = 0L;
        int start = pos - 1;
        for (int i = start; i >= 0; i--) {
            long temp = (0x000000FF & ((long) buf[i]));
            int offset = start - i;
            ret += (temp << (EIGHT * offset));
        }

        return ret;
    }

}
