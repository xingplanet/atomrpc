package com.xingplanet.atomrpc.rpc.server;

import com.xingplanet.atomrpc.rpc.constant.OperationCode;
import com.xingplanet.atomrpc.rpc.message.RpcRequestMessage;
import com.xingplanet.atomrpc.rpc.message.RpcResponseMessage;
import com.xingplanet.atomrpc.rpc.protocol.RpcProtocol;
import com.xingplanet.atomrpc.rpc.protocol.RpcProtocolBuilder;
import com.xingplanet.atomrpc.rpc.transcoder.SingleTranscoder;
import com.xingplanet.atomrpc.rpc.transcoder.Transcoder;

import java.lang.reflect.Method;

/**
 * @author wangjin
 */
public class SingleRpcServerExecutor extends AbstractRpcServerExecutor {

    @Override
    public RpcProtocol execute(RpcProtocol request) throws Exception {
        RpcResponseMessage body;
        long requestId = request.getRequestId();
        try {
            Transcoder transcoder = new SingleTranscoder();
            RpcRequestMessage message = transcoder.decode(request.getBody(), request.getSerializationType(), request.getCompressionType());
            Object instance = INSTANCE_MAP.get(message.getClassName());

            Class<?> clazz = instance.getClass();
            String methodName = message.getMethodName();
            Class<?>[] parameterTypes = message.getParameterTypes();
            Object[] parameters = message.getParameters();

            // jdk reflect
            Method method = clazz.getMethod(methodName, parameterTypes);
            method.setAccessible(true);
            Object result = method.invoke(instance, parameters);

            // cglib reflect
//            FastClass serviceFastClass = FastClass.create(clazz);
//            int methodIndex = serviceFastClass.getIndex(methodName, parameterTypes);
//            Object result = serviceFastClass.invoke(methodIndex, instance, parameters);

            body = new RpcResponseMessage(requestId, result);

        } catch (Throwable throwable) {
            body = new RpcResponseMessage(requestId, throwable);
        }
        return RpcProtocolBuilder.buildResponse(request, OperationCode.REMOTE_PROCEDURE_CALL_RESPONSE, body, new SingleTranscoder());
    }


}
