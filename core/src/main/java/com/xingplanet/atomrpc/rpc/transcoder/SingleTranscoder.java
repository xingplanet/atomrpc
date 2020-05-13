package com.xingplanet.atomrpc.rpc.transcoder;

import com.xingplanet.atomrpc.rpc.transcoder.compression.CompressionService;
import com.xingplanet.atomrpc.rpc.transcoder.compression.CompressionType;
import com.xingplanet.atomrpc.rpc.transcoder.compression.LZFCompressionServiceImpl;
import com.xingplanet.atomrpc.rpc.transcoder.serialization.JavaSerializationServiceImpl;
import com.xingplanet.atomrpc.rpc.transcoder.serialization.SerializationService;
import com.xingplanet.atomrpc.rpc.transcoder.serialization.SerializationType;

/**
 * 简单对象序列化和发序列化
 *
 * @author wangjin
 */
public class SingleTranscoder implements Transcoder {

    /**
     * java自带的序列化工具
     */
    private final SerializationService serializationService;

    /**
     * LZF压缩工具
     */
    private final CompressionService compressionService;

    /**
     * 压缩阀值，单位字节
     */
    private final int compressionThreshold;

    /**
     * 序列化构造器
     *
     * @param compressionThreshold 压缩阀值，单位字节
     */
    public SingleTranscoder(int compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
        this.serializationService = new JavaSerializationServiceImpl();
        this.compressionService = new LZFCompressionServiceImpl();
    }

    /**
     * 序列化构造器
     */
    public SingleTranscoder() {
        this(10 * 1024 * 1024);
    }

    @Override
    public byte[][] encode(Object value) throws Exception {
        byte[][] result = new byte[2][];
        byte[] symbol = new byte[2];
        // 使用序列化进行编码
        byte[] encodeValue = serializationService.encode(value);
        symbol[0] = SerializationType.JAVA;

        if (encodeValue.length > compressionThreshold) {
            encodeValue = compressionService.compress(encodeValue);
            symbol[1] = CompressionType.LZF;
        } else {
            symbol[1] = CompressionType.NONE;
        }
        result[0] = symbol;
        result[1] = encodeValue;

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T decode(byte[] encodeValue, byte serializationType, byte compressionType) throws Exception {
        switch (compressionType) {
            case CompressionType.LZF:
                encodeValue = compressionService.decompress(encodeValue);
                break;
            case CompressionType.NONE:
                break;
            default:
                throw new UnsupportedOperationException("unsupported compression: " + compressionType + ".");
        }
        switch (serializationType) {
            case SerializationType.JAVA:
                return (T) serializationService.decode(encodeValue);
            default:
                throw new UnsupportedOperationException("unsupported serialization: " + serializationType + ".");
        }
    }
}
