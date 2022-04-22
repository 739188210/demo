package com.nettystar.netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: miao
 * @date 2022/4/21
 */

public class GroupHeartBeatHandler extends ChannelInboundHandlerAdapter {
    public static final Logger logger = LoggerFactory.getLogger(GroupHeartBeatHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        String eventType = null;
        switch (event.state()){
            case READER_IDLE:
                eventType = "读空闲";
                logger.info("{} 超过心跳时间,关闭和服务端的连接:{}", eventType, ctx.channel().remoteAddress());
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                logger.info("{} 超过心跳时间,关闭和服务端的连接:{}", eventType, ctx.channel().remoteAddress());
                break;
            case ALL_IDLE:
                eventType = "读写空闲";
                logger.info("{} 超过心跳时间,关闭和服务端的连接:{}", eventType, ctx.channel().remoteAddress());
                break;
            default:
                logger.info("其他异常 超过心跳时间,关闭和服务端的连接:{}", ctx.channel().remoteAddress());
                super.userEventTriggered(ctx, evt);
        }

        ctx.channel().close();
    }
}
