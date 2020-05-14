package com.xingplanet.atomrpc.rpc.client.netty;

import com.xingplanet.atomrpc.rpc.client.RpcClientFactory;
import com.xingplanet.atomrpc.rpc.client.RpcFuture;
import com.xingplanet.atomrpc.rpc.message.RpcResponseMessage;
import com.xingplanet.atomrpc.rpc.protocol.RpcProtocol;
import com.xingplanet.atomrpc.rpc.transcoder.SingleTranscoder;
import com.xingplanet.atomrpc.rpc.transcoder.Transcoder;
import com.xingplanet.atomrpc.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author wangjin
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * 在到服务器的连接已经建立之后将被调用
     * @param ctx 参数
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",
                CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = msg.array();
        short headerLength =  ByteUtil.readShort(bytes, 6);
        int allLength =  ByteUtil.readInt(bytes, 2);
        int bodyLength = allLength - headerLength;

        byte[] header = new byte[headerLength];
        byte[] body = new byte[bodyLength];

        System.arraycopy(bytes, 0, header, 0, headerLength);
        System.arraycopy(bytes, headerLength, body, 0, bodyLength);

        RpcProtocol response = new RpcProtocol(header, body);
        RpcFuture future = RpcClientFactory.getFuture(response.getRequestId());

        Transcoder transcoder = new SingleTranscoder();
        RpcResponseMessage responseMessage = transcoder.decode(response.getBody(), response.getSerializationType(), response.getCompressionType());
        if (responseMessage.getThrowable() != null) {
            throw new Exception(responseMessage.getThrowable());
        }
        future.done(responseMessage.getResult());
        RpcClientFactory.removeFuture(response.getRequestId());
    }

    /**
     * 在处理过程中引发异常时被调用
     * @param ctx 处理器上下文
     * @param cause 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
