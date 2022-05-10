package com.nettystar.netty.dubborpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {

    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static ClientHandler clientHandler;

    private static void initClient() {
        clientHandler = new ClientHandler();

        NioEventLoopGroup loopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(clientHandler);
                    }
                });
        try {
            bootstrap.connect("127.0.0.1", 9000).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 编写方法使用代理模式，获取代理对象
    public Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass},
                ((proxy, method, args) -> {
                    if (clientHandler == null) {
                        initClient();
                    }

                    clientHandler.setRequestParam(providerName + args[0]);

                    return executor.submit(clientHandler).get();
                })
        );
    }
}
