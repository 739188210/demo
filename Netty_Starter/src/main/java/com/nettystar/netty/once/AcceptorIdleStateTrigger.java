package com.nettystar.netty.once;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author: miao
 * @date 2022/4/12
 */

public class AcceptorIdleStateTrigger extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AcceptorIdleStateTrigger.class);

    /**
     * 接收客户端发送消息， 拦截处理
     * @param ctx 上下文对象， 包含 pipeline ,  channel , address
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel().eventLoop().scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("channelRead has been third~" + (LocalDateTime.now().toString()) + "------Thread: " + Thread.currentThread().getId(), CharsetUtil.UTF_8));

        }, 5000, 10 * 1000, TimeUnit.MILLISECONDS);
        ByteBuf byteBuf = (ByteBuf) msg;
        logger.info("客户端发送消息： {}", byteBuf.toString(CharsetUtil.UTF_8));
        logger.info("客户端地址： {}", ctx.channel().remoteAddress());

        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("channelRead has been~" + (LocalDateTime.now().toString()) + "------Thread: " + Thread.currentThread().getId(), CharsetUtil.UTF_8));
        });

        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("channelRead has been twice~" + (LocalDateTime.now().toString()) + "------Thread: " + Thread.currentThread().getId(), CharsetUtil.UTF_8));
        });


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client~", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
