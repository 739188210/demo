package com.nettystar.netty.tcpstickyunpack.server;

import com.nettystar.netty.tcpstickyunpack.client.ClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @author: miao
 * @date 2022/4/25
 */

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ServerHandler());
    }
}
