package com.nettystar.netty.dubborpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class ClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;
    // 客户端调用方法的入参
    private String requestParam;

    // 与服务器建立连接后触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }
    // 收到服务器返回的消息后，调用方法
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
    // 被代理对象调用，发送数据给服务器 -》 wait -》 等待被(channelRead)唤醒 -》返回结果给代理对象
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(requestParam);
        wait();
        return result;
    }

    void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }
}
