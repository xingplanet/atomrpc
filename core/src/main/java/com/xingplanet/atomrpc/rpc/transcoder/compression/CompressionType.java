package com.xingplanet.atomrpc.rpc.transcoder.compression;

/**
 * 压缩方式
 *
 * @author wangjin
 */
public class CompressionType {
    private CompressionType() {

    }

    /**
     * 为使用压缩算法
     */
    public static final byte NONE = 0;

    /**
     * LZF 压缩算法
     */
    public static final byte LZF = 1;


}
