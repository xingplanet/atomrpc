package com.xingplanet.atomrpc.rpc.protocol;

import com.xingplanet.atomrpc.util.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static com.xingplanet.atomrpc.rpc.protocol.RpcProtocol.DEFAULT_HEADER_LENGTH;

/**
 * @author wangjin
 */
public class RpcProtocolParse {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcProtocolParse.class);

    private final InputStream inputStream;

    public RpcProtocolParse(InputStream inputStream) throws NullPointerException {
        if (inputStream == null) {
            LOGGER.error("create RpcProtocolParse fail: inputStream is null.");
            throw new NullPointerException("create RpcProtocolParse fail: inputStream is null.");
        }
        this.inputStream = inputStream;
    }

    /**
     * 传输时，对协议进行解析
     *
     * @return 协议包
     * @throws IOException io异常
     */
    public RpcProtocol parse() throws IOException {

        int pos = 0;
        byte[] header;
        byte[] body = null;
        byte[] fixedHeader = new byte[DEFAULT_HEADER_LENGTH];
        byte[] dynamicHeader = null;

        // 固定header
        while (pos < DEFAULT_HEADER_LENGTH) {
            int readByteNum = inputStream.read(fixedHeader, pos, DEFAULT_HEADER_LENGTH - pos);
            if (readByteNum > 0) {
                pos += readByteNum;
            } else {
                return null;
            }
        }
        // 动态可伸缩header
        int headerLength = ByteUtil.readInt(fixedHeader, 6);
        int dynamicHeaderLength = headerLength - DEFAULT_HEADER_LENGTH;
        if (dynamicHeaderLength > 0) {
            dynamicHeader = new byte[dynamicHeaderLength];
            while (pos < headerLength) {
                int readByteNum = inputStream.read(dynamicHeader, pos, headerLength - pos);
                if (readByteNum > 0) {
                    pos += readByteNum;
                } else {
                    return null;
                }
            }
        }

        // body
        int allLength = ByteUtil.readInt(fixedHeader, 2);
        int bodyLength = allLength - headerLength;
        if (bodyLength > 0) {
            body = new byte[bodyLength];
            while (pos < allLength) {
                int readByteNum = inputStream.read(body, pos, allLength - pos);
                if (readByteNum > 0) {
                    pos += readByteNum;
                } else {
                    return null;
                }
            }
        }

        header = new byte[headerLength];
        System.arraycopy(fixedHeader, 0, header, 0, DEFAULT_HEADER_LENGTH);
        if (dynamicHeader != null) {
            System.arraycopy(dynamicHeader, 0, header, DEFAULT_HEADER_LENGTH, dynamicHeaderLength);
        }

        return new RpcProtocol(header, body);
    }

}
