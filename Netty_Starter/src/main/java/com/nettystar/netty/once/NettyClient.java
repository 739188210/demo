package com.nettystar.netty.once;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: miao
 * @date 2022/4/12
 */

public class NettyClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private static final int port = 6666;

    public static void main(String[] args) {
        // bossGroup 处理连接  workerGroup 处理业务
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 创建客户端的启动对象Bootstrap，配置参数
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientInboundHandler());
                        }
                    });

            logger.info("client has been already...");

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", port).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully ();
        }

    }
}
