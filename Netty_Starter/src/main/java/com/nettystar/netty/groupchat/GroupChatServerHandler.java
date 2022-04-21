package com.nettystar.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: miao
 * @date 2022/4/21
 */

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    public static final Logger logger = LoggerFactory.getLogger(GroupChatServerHandler.class);
    // 定义一个channel组， 管理所有channel
    // GlobalEventExecutor.INSTANCE 是全局时间执行器， 是单例的
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 用于 当连接建立，该方法第一个被执行
     * 将当前channel加入channelGroup
     *
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channelGroup.add(channel);
        // 遍历channelGroup中所有channel，并发送消息
        channelGroup.writeAndFlush("客户端： " + channel.remoteAddress() + "进入群聊\n");
    }

    /**
     * 断开连接，将消息推送给剩余在线用户
     * @param ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("客户端： " + channel.remoteAddress() + "退出群聊\n");

    }

    /**
     * 表示channel处于活跃状态
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.warn("用户 {} 上线了...", ctx.channel().remoteAddress());
    }

    /**
     * 表示 channel处于不活跃状态
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.warn("用户 {} 离线了...", ctx.channel().remoteAddress());

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush("客户： " + channel.remoteAddress() + " 发送消息： " + msg);
            } else {
                ch.writeAndFlush("消息已发送： " + msg);
            }
        });

    }
}
