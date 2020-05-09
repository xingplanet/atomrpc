package com.xingplanet.atomrpc.rpc.transcoder;

/**
 * 序列化与反序列化接口
 *
 * @author wangjin
 */
public interface Transcoder {

    /**
     * 在传输之前，需要将对象进行序列化
     *
     * @param value 需要序列化的对象
     * @return data[0][0] 保存序列化方式，data[0][1] 保存压缩方式，data[1] 序列化之后的数组
     * @throws Exception 编码中出现异常
     */
    byte[][] encode(Object value) throws Exception;

    /**
     * 将编码数据，根据对应的反序列化方式和解压方式，获得对象
     *
     * @param encodeValue       编码数据
     * @param serializationType 序列化方式 {@link com.xingplanet.atomrpc.rpc.transcoder.serialization.SerializationType}
     * @param compressionType   压缩方式 {@link com.xingplanet.atomrpc.rpc.transcoder.compression.CompressionType}
     * @return 对象，编码数据反序列化之后的对象
     * @throws Exception 反编码中出现异常
     */
    <T> T decode(byte[] encodeValue, byte serializationType, byte compressionType) throws Exception;

}
