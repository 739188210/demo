package com.nettystar.netty.mycodec.client;

import com.nettystar.netty.groupchat.GroupChatClient;
import com.nettystar.netty.groupchat.GroupChatClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: miao
 * @date 2022/4/24
 */

public class MyCodecClient {
    private static final int port = 9000;
    public static final Logger logger = LoggerFactory.getLogger(MyCodecClient.class);

    public static void main(String[] args) {
        new MyCodecClient().run();
    }

    public void run() {

        EventLoopGroup loopGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());


            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", port).sync();
            Channel channel = channelFuture.channel();
            logger.info("------- {} ---------", channel.remoteAddress());
            channel.closeFuture().sync();

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }

    }
}
