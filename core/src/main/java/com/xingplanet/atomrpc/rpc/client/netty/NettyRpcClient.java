package com.xingplanet.atomrpc.rpc.client.netty;

import com.xingplanet.atomrpc.rpc.client.RpcClient;
import com.xingplanet.atomrpc.rpc.client.RpcClientFactory;
import com.xingplanet.atomrpc.rpc.client.RpcFuture;
import com.xingplanet.atomrpc.rpc.protocol.RpcProtocol;
import com.xingplanet.atomrpc.rpc.server.netty.NettyRpcServer;
import com.xingplanet.atomrpc.util.SpringBeanFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

/**
 * @author wangjin
 */
public class NettyRpcClient implements RpcClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRpcServer.class);

    private final EventLoopGroup group = new NioEventLoopGroup();

    private final String host;
    private final int port;

    private Channel channel;

    public NettyRpcClient(String ip) throws Exception {
        this.host = ip.split(":")[0];
        this.port = Integer.parseInt(ip.split(":")[1]);
        start();
    }

    @Override
    public Object execute(Method method, Object[] args) throws Exception {
        RpcFuture rpcFuture = new RpcFuture();

        ExecutorService executorService = SpringBeanFactory.getBean(ExecutorService.class);
        executorService.submit(() -> {
            try {
                RpcProtocol rpcProtocol = RpcClientFactory.buildProtocol(method, args);
                RpcClientFactory.setFuture(rpcProtocol.getRequestId(), rpcFuture);

                byte[] all = new byte[rpcProtocol.getProtocolLength()];
                System.arraycopy(rpcProtocol.getHeader(), 0, all, 0, rpcProtocol.getHeaderLength());
                System.arraycopy(rpcProtocol.getBody(), 0, all, rpcProtocol.getHeaderLength(), rpcProtocol.getProtocolLength() - rpcProtocol.getHeaderLength());
                ByteBuf byteBuf = Unpooled.copiedBuffer(all);

                channel.writeAndFlush(byteBuf);
            } catch (Exception e) {
                LOGGER.error("buildProtocol error: {}.", e.getMessage(), e);
            }
        });
        return rpcFuture.get();
    }

    @Override
    public void start() throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioServerSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .option(ChannelOption.SO_BACKLOG, 128)
                .handler(new RpcClientInitializer());

        ChannelFuture future = bootstrap.connect().sync();
        this.channel = future.channel();
        this.channel.closeFuture().sync();
    }

    @Override
    public void destroy() {
        group.shutdownGracefully().syncUninterruptibly();
        LOGGER.info("NettyRpcClient closed.");
    }


}
