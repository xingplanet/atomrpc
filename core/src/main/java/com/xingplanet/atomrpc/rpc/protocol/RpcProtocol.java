package com.xingplanet.atomrpc.rpc.protocol;

/**
 * rpc协议定义
 * <p>协议包含协议头和协议体，
 * 协议头内容：
 * 魔法位置（16bit）、整体长度（32bit）、协议头长度（16bit）、协议版本（8bit）、消息类型（8bit）、序列化方式（8bit）、压缩方式（8bit）、
 * 消息ID（24bit）、扩展协议字段（不定长）
 * 协议体内容：
 * payload
 * <p/>
 *
 * @author wangjin
 */
public class RpcProtocol {

    private static final short MAGIC = (short) 0xadee;

    private static final byte REQUEST = 0x01;

    private static final byte RESPONSE = 0x02;


}
