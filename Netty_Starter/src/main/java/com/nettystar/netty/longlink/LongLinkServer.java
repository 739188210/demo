package com.nettystar.netty.longlink;

import com.nettystar.netty.groupchat.GroupChatServer;
import com.nettystar.netty.groupchat.GroupChatServerHandler;
import com.nettystar.netty.groupchat.GroupHeartBeatHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author: miao
 * @date 2022/4/22
 */

public class LongLinkServer {
    private static final int port = 9000;
    public static final Logger logger = LoggerFactory.getLogger(LongLinkServer.class);


    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture channelFuture = null;
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // ??????http?????? ???????????????
                            pipeline.addLast("httpcodec", new HttpServerCodec());
                            // ???????????????????????????ChunkedWriteHandler?????????
                            pipeline.addLast(new ChunkedWriteHandler());
                            //http ??????????????? HttpObjectAggregator????????????????????????
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            // ??? ?????????websocket ????????? ???(frame) ????????????
                            // WebSocketServerProtocolHandler ?????? 1. ?????? uri   2.???http???????????????ws??????
                            pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                            // ????????? ?????????(text frame) ????????????????????????
                            pipeline.addLast("textFrameHandler", new MyTextFrameHandler());
                        }
                    });

            ChannelFuture sync = bootstrap.bind(port).sync();

            logger.info("server has been started");

            sync.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
