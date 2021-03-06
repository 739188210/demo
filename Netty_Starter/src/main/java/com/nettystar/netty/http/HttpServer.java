package com.nettystar.netty.http;

import com.nettystar.netty.once.AcceptorIdleStateTrigger;
import com.nettystar.netty.once.NettyServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author: miao
 * @date 2022/4/19
 */

public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private static final int port = 9000;

    public static void main(String[] args) {
        runServer();
    }

    public static void runServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        ChannelFuture channelFuture = null;
        try {
            // server 启动引导
            bootstrap.group(bossGroup, workerGroup)
                    // NioServerSocketChannel 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 临时存放已完成三次握手的请求的队列的最大长度。
                    // 如果未设置或所设置的值小于1，Java将使用默认值50。
                    // 如果大于队列的最大长度，请求会被拒绝
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new HTTPServerInitializer());
            logger.info("Server start listen at " + port);
            // 绑定端口并同步 启动服务器
            channelFuture = bootstrap.bind(port).sync();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
