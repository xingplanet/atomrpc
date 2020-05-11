package com.xingplanet.atomrpc.rpc.protocol;

import com.xingplanet.atomrpc.rpc.constant.OperationCode;
import com.xingplanet.atomrpc.util.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * rpc协议定义
 * <p>协议包含协议头和协议体，
 * 协议头内容：header 固定长为 20byte
 * 魔法位置（16bit = 2byte）、整体长度（32bit = 4byte）、协议头长度（16bit = 2byte）、协议版本（8bit = 1byte）、消息类型（8bit = 1byte）、
 * 序列化方式（8bit = 1byte）、压缩方式（8bit = 1byte）、消息ID（64bit = 8byte）、扩展协议字段（不定长）
 * 协议体内容：
 * payload
 * <p/>
 *
 * @author wangjin
 */
public class RpcProtocol {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcProtocol.class);

    /**
     * 固定协议头长度
     */
    public static final int DEFAULT_HEADER_LENGTH = 20;

    /**
     * 魔法位
     */
    private static final short MAGIC = (short) 0xadee;

    /**
     * 协议版本
     */
    private static final byte VERSION = 0x01;

    private final byte[] header;

    private final byte[] body;

    public RpcProtocol(byte[] header, byte[] body) throws IllegalArgumentException {
        // TODO 校验
        this.header = header;

        this.setMagic();
        this.setVersion();
        if (body != null) {
            this.body = body;
        } else {
            this.body = new byte[0];
        }
    }

    /**
     * 获取协议头
     *
     * @return 协议头
     */
    public byte[] getHeader() {
        return this.header;
    }

    /**
     * 获取协议体
     *
     * @return 协议体
     */
    public byte[] getBody() {
        return this.body;
    }

    /**
     * 获取序列化类型
     *
     * @return 序列化类型
     */
    public byte getSerializationType() {
        return this.header[10];
    }

    /**
     * 获取压缩类型
     *
     * @return 压缩类型
     */
    public byte getCompressionType() {
        return this.header[11];
    }

    /**
     * 获取消息id
     *
     * @return 消息id
     */
    public long getRequestId() {
        return ByteUtil.readLong(this.header, 12);
    }

    /**
     * 请求类型
     *
     * @return 网络通信操作代码
     * @see OperationCode
     */
    public byte getOperationCode() {
        return this.header[9];
    }

    /**
     * 请求的整体长度
     *
     * @return 请求整体长度
     */
    public int getRequestLength() {
        return ByteUtil.readInt(this.header, 2);
    }

    /**
     * 设置魔法位
     */
    public void setMagic() {
        ByteUtil.writeShort(MAGIC, this.header, 0);
    }

    /**
     * 请求的魔法位
     *
     * @return 请求魔法位
     */
    public short getMagic() {
        return ByteUtil.readShort(this.header, 0);
    }

    /**
     * 设置请求协议
     */
    public void setVersion() {
        this.header[8] = VERSION;
    }

    /**
     * 请求的协议版本
     *
     * @return 请求协议版本
     */
    public byte getVersion() {
        return this.header[8];
    }

    @Override
    public String toString() {
        return "RpcProtocol{" +
                "header=" + Arrays.toString(header) +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
