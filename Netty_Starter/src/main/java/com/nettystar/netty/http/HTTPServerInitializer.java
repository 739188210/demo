package com.nettystar.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author: miao
 * @date 2022/4/19
 */

public class HTTPServerInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // pipeline 添加处理器
        ChannelPipeline pipeline = ch.pipeline();
        // 加入一个netty 提供的httpServerCodec 编-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
    }
}
