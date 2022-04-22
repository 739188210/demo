package com.nettystar.netty.once;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author: miao
 * @date 2022/4/12
 */

public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private static final int port = 6666;

    public static void main(String[] args) {
        // bossGroup 处理连接  workerGroup 处理业务
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        ChannelFuture channelFuture = null;
        try {
            // server 启动引导
            bootstrap.group(bossGroup, workerGroup)
                        // NioServerSocketChannel 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                        // 临时存放已完成三次握手的请求的队列的最大长度。
                        // 如果未设置或所设置的值小于1，Java将使用默认值50。
                        // 如果大于队列的最大长度，请求会被拒绝
                    // 对应tcp/ip协议listen函数中的backlog参数。函数listen(int socketfd,int backlog)用来初始化服务器可连接队列。
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 是否将小的数据包组装为更大的帧然后发送  true(default)组装； false 不组装，时效性高
                    // 对应TCP_CORK 等到发送的数据量最大的时候一次性发送数据，适用于文件传输。
                    .option(ChannelOption.TCP_NODELAY, false)
                    // 阻塞close() 直到延迟时间到或发送缓冲区中的数据发送完毕
                    // 0表示OS放弃发送缓冲区的数据直接向对端发送RST包，对端收到复位错误，socket.close()方法立即返回
                    .option(ChannelOption.SO_LINGER, 30)
                    //
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS))
//                                    .addLast("decoder", new StringDecoder())
//                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new AcceptorIdleStateTrigger())
                            ;
                        }
                    });
            logger.info("Server start listen at " + port);
            // 绑定端口并同步 启动服务器
            channelFuture = bootstrap.bind(port).sync();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
