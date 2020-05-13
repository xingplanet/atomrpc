package com.xingplanet.atomrpc.rpc.server.netty;

import com.xingplanet.atomrpc.rpc.protocol.RpcProtocol;
import com.xingplanet.atomrpc.rpc.server.SingleRpcServerExecutor;
import com.xingplanet.atomrpc.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author wangjin
 */
@ChannelHandler.Sharable
public class RpcServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 在到服务器的连接已经建立之后将被调用
     * @param ctx 参数
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",
                CharsetUtil.UTF_8));
    }

    /**
     * 当从服务器接收到一条消息时被调用
     * @param ctx 处理器上下文
     * @param msg 消息
     */
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

        SingleRpcServerExecutor executor = new SingleRpcServerExecutor();
        RpcProtocol request = new RpcProtocol(header, body);
        RpcProtocol response = executor.execute(request);

        byte[] resBytes = new byte[response.getProtocolLength()];
        System.arraycopy(response.getHeader(), 0, resBytes, 0, response.getHeaderLength());
        System.arraycopy(response.getBody(), 0, resBytes, response.getHeaderLength(), response.getProtocolLength() - response.getHeaderLength());

        ByteBuf byteBuf = Unpooled.wrappedBuffer(resBytes);
        ctx.writeAndFlush(byteBuf);
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
