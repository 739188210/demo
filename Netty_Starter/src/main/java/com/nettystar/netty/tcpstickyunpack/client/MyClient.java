package com.nettystar.netty.tcpstickyunpack.client;

import com.nettystar.netty.mycodec.client.MyCodecClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: miao
 * @date 2022/4/24
 */

public class MyClient {

    private static final int port = 9000;
    public static final Logger logger = LoggerFactory.getLogger(MyClient.class);

    public static void main(String[] args) {
        new MyClient().run();
    }

    public void run() {

        EventLoopGroup loopGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(loopGroup).channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", port).sync();
            logger.info("------- {} ---------", channelFuture.channel().remoteAddress());

            channelFuture.channel().closeFuture().sync();

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }

    }
}
