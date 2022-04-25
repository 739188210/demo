package com.nettystar.netty.mycodec.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: miao
 * @date 2022/4/24
 */

public class ClientHandler extends SimpleChannelInboundHandler<Long> {
    public static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        logger.info("client handler receive msg: {}", msg);
    //    ctx.channel().writeAndFlush(2123123);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("client handler send msg");
        ctx.writeAndFlush(2123123L);
    }
}
