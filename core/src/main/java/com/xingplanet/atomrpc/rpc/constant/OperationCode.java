package com.xingplanet.atomrpc.rpc.constant;

/**
 * @author wangjin
 */
public class OperationCode {

    private OperationCode() {

    }

    /**
     * 远程服务调用-请求
     */
    private static final byte REMOTE_PROCEDURE_CALL_REQUEST = 0x01;

    /**
     * 远程服务调用-响应
     */
    private static final byte REMOTE_PROCEDURE_CALL_RESPONSE = 0x02;

    /**
     * 心跳-请求
     */
    private static final byte HEARTBEAT_REQUEST = 0x03;

    /**
     * 心跳-响应
     */
    private static final byte HEARTBEAT_RESPONSE = 0x04;

}
