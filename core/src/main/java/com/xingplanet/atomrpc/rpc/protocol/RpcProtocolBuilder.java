package com.xingplanet.atomrpc.rpc.protocol;

import com.xingplanet.atomrpc.rpc.constant.OperationCode;
import com.xingplanet.atomrpc.rpc.transcoder.Transcoder;
import com.xingplanet.atomrpc.util.ByteUtil;

import static com.xingplanet.atomrpc.rpc.protocol.RpcProtocol.DEFAULT_HEADER_LENGTH;

/**
 * @author wangjin
 */
public class RpcProtocolBuilder {

    private RpcProtocolBuilder() {
    }

    /**
     * 创建RPC请求包，不带请求内容
     *
     * @param requestId     消息id
     * @param operationCode 操作类型{@link OperationCode}
     * @return 请求包
     * @throws Exception 创建请求包的异常
     */
    public static RpcProtocol buildRequest(long requestId, byte operationCode) throws Exception  {
        // 协议头没有使用扩展字段
        byte[] header = new byte[DEFAULT_HEADER_LENGTH];

        // 协议头长度
        ByteUtil.writeShort(DEFAULT_HEADER_LENGTH, header, 6);
        // 协议整体长度
        ByteUtil.writeInt(DEFAULT_HEADER_LENGTH, header, 2);

        // 消息id
        ByteUtil.writeLong(requestId, header, 12);
        // 请求类型
        header[9] = operationCode;
        return new RpcProtocol(header, null);
    }

    /**
     * 创建RPC请求包
     *
     * @param requestId     消息ID
     * @param operationCode 操作类型{@link OperationCode}
     * @param body          请求内容
     * @param transcoder    编码方式
     * @return 请求包
     * @throws Exception 创建请求包的异常
     */
    public static RpcProtocol buildRequest(long requestId, byte operationCode, Object body, Transcoder transcoder) throws Exception {
        byte[][] encodeByte = transcoder.encode(body);
        // 协议头没有使用扩展字段
        byte[] header = new byte[DEFAULT_HEADER_LENGTH];

        // 序列化
        header[10] = encodeByte[0][0];
        // 压缩方式
        header[11] = encodeByte[0][1];

        // 协议头长度
        ByteUtil.writeShort(DEFAULT_HEADER_LENGTH, header, 6);
        // 协议整体长度
        ByteUtil.writeInt(DEFAULT_HEADER_LENGTH + encodeByte[1].length, header, 2);

        // 消息id
        ByteUtil.writeLong(requestId, header, 12);
        // 请求类型
        header[9] = operationCode;
        return new RpcProtocol(header, encodeByte[1]);
    }

    /**
     * 创建RPC响应包，消息ID和请求包的保持一致
     *
     * @param request       请求包
     * @param operationCode 操作类型{@link OperationCode}
     * @return 响应包
     * @throws Exception 创建响应包异常
     */
    public static RpcProtocol buildResponse(RpcProtocol request, byte operationCode) throws Exception {
        // 协议头没有使用扩展字段
        byte[] header = new byte[DEFAULT_HEADER_LENGTH];

        // 协议头长度
        ByteUtil.writeShort(DEFAULT_HEADER_LENGTH, header, 6);
        // 协议整体长度
        ByteUtil.writeInt(DEFAULT_HEADER_LENGTH, header, 2);

        // 消息id
        ByteUtil.writeLong(request.getRequestId(), header, 12);
        // 请求类型
        header[9] = operationCode;
        return new RpcProtocol(header, null);
    }


    /**
     * 创建RPC响应包，消息ID和请求包的保持一致
     *
     * @param request       请求包
     * @param operationCode 操作类型{@link OperationCode}
     * @param body          请求内容
     * @param transcoder    编码方式
     * @return 响应包
     * @throws Exception 创建响应包异常
     */
    public static RpcProtocol buildResponse(RpcProtocol request, byte operationCode, Object body, Transcoder transcoder) throws Exception {

        // 协议头没有使用扩展字段
        byte[] header = new byte[DEFAULT_HEADER_LENGTH];
        byte[][] encodeByte = transcoder.encode(body);

        // 序列化
        header[10] = encodeByte[0][0];
        // 压缩方式
        header[11] = encodeByte[0][1];

        // 协议头长度
        ByteUtil.writeShort(DEFAULT_HEADER_LENGTH, header, 6);
        // 协议整体长度
        ByteUtil.writeInt(DEFAULT_HEADER_LENGTH + encodeByte[1].length, header, 2);

        // 消息id
        ByteUtil.writeLong(request.getRequestId(), header, 12);
        // 请求类型
        header[9] = operationCode;
        return new RpcProtocol(header, encodeByte[1]);
    }
}
