package com.nettystar.netty.mycodec.client;

import com.nettystar.netty.mycodec.server.ByteToLongDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author: miao
 * @date 2022/4/24
 */

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new LongToByteEncoder());

        pipeline.addLast(new ByteToLongDecoder());

        pipeline.addLast(new ClientHandler());

    }
}
