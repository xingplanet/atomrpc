package com.xingplanet.atomrpc.rpc.client.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author wangjin
 */
public class RpcClientInitializer extends ChannelInitializer<SocketChannel> {

    private final RpcClientHandler rpcClientHandler = new RpcClientHandler();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // TODO 添加心跳
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536, 2, 4, -20, 0));
        ch.pipeline().addLast(rpcClientHandler);
    }
}
