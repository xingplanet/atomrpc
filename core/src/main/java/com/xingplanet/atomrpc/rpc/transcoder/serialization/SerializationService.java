package com.xingplanet.atomrpc.rpc.transcoder.serialization;

/**
 * 提供对象的序列化和反序列化
 *
 * @author wangjin
 */
public interface SerializationService {

    /**
     * 将对象字节进行编码返回
     *
     * @param value 对象
     * @return 编码值
     * @throws Exception 异常
     */
    byte[] encode(Object value) throws Exception;

    /**
     * 将编码字符转回对象
     *
     * @param encodedBytes 编码值
     * @return 对象
     * @throws Exception 异常
     */
    Object decode(byte[] encodedBytes) throws Exception;
}
