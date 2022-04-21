package com.nettystar.netty.heartbeat;

import com.nettystar.netty.groupchat.GroupChatServer;
import com.nettystar.netty.groupchat.GroupChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author: miao
 * @date 2022/4/21
 */

public class HeartBeatServer {
    private static final int port = 9000;
    public static final Logger logger = LoggerFactory.getLogger(HeartBeatServer.class);

    public static void main(String[] args) {
        new HeartBeatServer().run();
    }



    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture channelFuture = null;
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            /*
                            IdleStateHandler : 用于处理空闲状态的处理器， 心跳检测
                            param - allIdleTime 多久没有进行读和写，就会发送一个心跳包
                            IdleStateEvent 触发后，会将该时间进一步交由下一个handler的 userEventTriggered（）去处理读&写空闲
                             */
                            pipeline.addLast("heartBeatHandler",
                                    new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            pipeline.addLast("deepHeartBeatHandler", new DeeperHeartBeatHandler());

                        }
                    });

            channelFuture = bootstrap.bind(port).sync();

            logger.info("server has been started");

            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
