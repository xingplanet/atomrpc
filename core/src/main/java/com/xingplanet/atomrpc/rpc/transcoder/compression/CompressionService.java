package com.xingplanet.atomrpc.rpc.transcoder.compression;

/**
 * @author wangjin
 */
public interface CompressionService {

    /**
     * 对源数据进行压缩
     *
     * @param srcData 源数据
     * @return 压缩后对数据
     * @throws Exception 压缩时的异常
     */
    byte[] compress(byte[] srcData) throws Exception;

    /**
     * 对压缩数据进行还原
     *
     * @param compressData 压缩数据
     * @return 源数据
     * @throws Exception 解压时的异常
     */
    byte[] decompress(byte[] compressData) throws Exception;
}
