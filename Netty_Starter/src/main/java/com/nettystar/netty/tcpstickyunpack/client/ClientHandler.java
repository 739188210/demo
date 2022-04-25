package com.nettystar.netty.tcpstickyunpack.client;

import com.nettystar.netty.tcpstickyunpack.server.ServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author: miao
 * @date 2022/4/25
 */

public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    public static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        if (msg.isReadable()) {
            byte[] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);

            String str = new String(bytes, StandardCharsets.UTF_8);
            logger.info("client receive data: {}", str);
            logger.info("client receive data times: {}", ++ count);

        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello server " + i, StandardCharsets.UTF_8);
            ctx.writeAndFlush(byteBuf);
        }
    }
}
