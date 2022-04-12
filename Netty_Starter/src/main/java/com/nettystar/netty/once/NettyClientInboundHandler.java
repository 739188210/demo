package com.nettystar.netty.once;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: miao
 * @date 2022/4/12
 */

public class NettyClientInboundHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(NettyClientInboundHandler.class);

    /**
     * 当通道就绪时，触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, server~~~~", CharsetUtil.UTF_8));
    }

    /**
     * 通道有读取事件时，触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
   //     super.channelRead(ctx, msg);
        ByteBuf byteBuf = (ByteBuf) msg;
        logger.info("client get message: {}", byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 异常触发
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
