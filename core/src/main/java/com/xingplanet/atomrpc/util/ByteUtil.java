package com.xingplanet.atomrpc.util;

import java.util.Arrays;

/**
 * byte 转换工具
 *
 * @author wangjin
 */
public class ByteUtil {

    private ByteUtil() {
    }

    /**
     * 将String转换为byte数组，写入指定的目标数组中
     *
     * @param value  需要被转换的String对象
     * @param target 目标数组
     * @param offset 数组的起始索引，最小从0开始
     */
    public static void writeString(String value, byte[] target, int offset) {
        byte[] valueByte = value.getBytes();
        for (byte b : valueByte) {
            target[offset++] = b;
        }
    }

    /**
     * 将byte数组转换成String，并返回
     *
     * @param src    byte数组
     * @param offset 数组起始索引，最小从0开始
     * @param length 需要被转换的长度
     * @return String对象
     */
    public static String readString(byte[] src, int offset, int length) {
        byte[] temp = new byte[length];
        System.arraycopy(src, offset, temp, 0, length);
        return Arrays.toString(temp);
    }

    /**
     * 将short转换为byte数组，写入指定的目标数组中
     *
     * @param value  需要被转换的short值
     * @param target 目标数组
     * @param offset 数组的起始索引，最小从0开始
     */
    public static void writeShort(int value, byte[] target, int offset) {
        target[offset++] = (byte) (value >> 8);
        target[offset] = (byte) value;
    }

    /**
     * 将byte数组转换成short值
     *
     * @param src    源数据
     * @param offset 数组起始索引，最小从0开始
     * @return short值
     */
    public static short readShort(byte[] src, int offset) {
        return (short) ((src[offset++] << 8) | src[offset] & 0xff);
    }

    /**
     * 将int转换为byte数组，写入指定的目标数组中
     *
     * @param value  需要被转换的int值
     * @param target 目标数组
     * @param offset 数组的起始索引，最小从0开始
     */
    public static void writeInt(int value, byte[] target, int offset) {
        target[offset++] = (byte) (value >> 24);
        target[offset++] = (byte) (value >> 16);
        target[offset++] = (byte) (value >> 8);
        target[offset] = (byte) value;
    }

    /**
     * 将byte数组转换成int值
     *
     * @param src    源数据
     * @param offset 数组起始索引，最小从0开始
     * @return int值
     */
    public static int readInt(byte[] src, int offset) {
        return ((src[offset++] << 24) | ((src[offset++] & 0xff) << 16) |
                ((src[offset++] & 0xff) << 8) | src[offset] & 0xff);
    }

    /**
     * 将long转换为byte数组，写入指定的目标数组中
     *
     * @param value  需要被转换的long值
     * @param target 目标数组
     * @param offset 数组的起始索引，最小从0开始
     */
    public static void writeLong(long value, byte[] target, int offset) {
        target[offset++] = (byte) (value >> 56);
        target[offset++] = (byte) (value >> 48);
        target[offset++] = (byte) (value >> 40);
        target[offset++] = (byte) (value >> 32);
        target[offset++] = (byte) (value >> 24);
        target[offset++] = (byte) (value >> 16);
        target[offset++] = (byte) (value >> 8);
        target[offset] = (byte) value;
    }

    /**
     * 将byte数组转换成long值
     *
     * @param src    源数据
     * @param offset 数组起始索引，最小从0开始
     * @return long值
     */
    public static long readLong(byte[] src, int offset) {
        return ((long)src[offset++] << 56) + ((long)(src[offset++] & 0xff) << 48) +
                ((long)(src[offset++] & 0xff) << 40) + ((long)(src[offset++] & 0xff) << 32) +
                ((src[offset++] & 0xff) << 24) + ((src[offset++] & 0xff) << 16) |
                ((src[offset++] & 0xff) << 8) + src[offset] & 0xff;
    }


}
