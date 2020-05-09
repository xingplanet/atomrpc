package com.xingplanet.atomrpc.rpc.transcoder.compression;

import com.ning.compress.lzf.LZFDecoder;
import com.ning.compress.lzf.LZFEncoder;

/**
 * LZF 压缩算法
 *
 * @author wangjin
 */
public class LZFCompressionServiceImpl implements CompressionService {

    @Override
    public byte[] compress(byte[] srcData) throws Exception {
        return LZFEncoder.encode(srcData);
    }

    @Override
    public byte[] decompress(byte[] compressData) throws Exception {
        return LZFDecoder.decode(compressData);
    }
}
