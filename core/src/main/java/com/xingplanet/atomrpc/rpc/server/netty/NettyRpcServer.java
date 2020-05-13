package com.xingplanet.atomrpc.rpc.server.netty;

import com.xingplanet.atomrpc.rpc.server.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author wangjin
 */
public class NettyRpcServer implements RpcServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRpcServer.class);

    private final int port;

    private final EventLoopGroup boss = new NioEventLoopGroup();
    private final EventLoopGroup work = new NioEventLoopGroup();

    public NettyRpcServer(int port) {
        this.port = port;
    }
    @Override
    public void start() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work).channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(this.port))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new RpcServerInitializer());

        ChannelFuture future = bootstrap.bind().sync();
        future.channel().closeFuture().sync();
    }

    @Override
    public void destroy() {
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
        LOGGER.info("ServerBootstrap closed.");
    }

}
