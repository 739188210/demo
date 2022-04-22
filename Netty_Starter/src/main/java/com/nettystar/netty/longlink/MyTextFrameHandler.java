package com.nettystar.netty.longlink;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: miao
 * @date 2022/4/22
 */

public class MyTextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static final Logger logger = LoggerFactory.getLogger(MyTextFrameHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        logger.info("服务器 收到消息：{}", msg.text());

        ctx.channel().writeAndFlush("服务器时间：" + LocalDateTime.now() + "  收到消息");
    }
}
