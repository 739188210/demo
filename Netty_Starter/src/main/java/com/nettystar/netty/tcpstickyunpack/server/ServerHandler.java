package com.nettystar.netty.tcpstickyunpack.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author: miao
 * @date 2022/4/25
 */

public class ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    public static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        if (msg.isReadable()) {
            byte[] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);

            String str = new String(bytes, StandardCharsets.UTF_8);
            logger.info("server receive data: {}", str);
            logger.info("server receive data times: {}", ++ count);

            ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), StandardCharsets.UTF_8);

            ctx.writeAndFlush(byteBuf);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
