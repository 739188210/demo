package com.nettystar.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @author: miao
 * @date 2022/4/11
 */

public class GroupChatServer {

    private static final Logger logger = LoggerFactory.getLogger(GroupChatServer.class);
    private static final int PORT = 6666;

    private Selector selector;

    private ServerSocketChannel listenChannel;


    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();

            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            listenChannel.configureBlocking(false);
            // 将该channel 注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        logger.info("selector is waitting...");
        try {
            while (true) {
                int count = selector.select(2000);
                // 有事件处理
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        // 监听到accept
                        if (selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            // 注册到selector OP_READ
                            socketChannel.register(selector, SelectionKey.OP_READ);

                            logger.info(socketChannel.getRemoteAddress() + "  上线了...");

                        }
                        // 判断通道是否为可读状态
                        if (selectionKey.isReadable()) {
                            readData(selectionKey);
                        }
                        // 防止重复操作
                        iterator.remove();
                    }

                } else {
//                    logger.info("selector is waitting...");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void readData(SelectionKey key) {
        // 取到关联的channel
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            if (count > 0) {
                String msg = new String(buffer.array());
                logger.info("Server got msg: " + msg);
                // 转发给其他客户端 channel
                sendMassMessage(msg, channel);
            }

        } catch (IOException e) {
            try {
                logger.info(channel.getRemoteAddress() + " 离线了...");

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    // 取消注册
                    key.cancel();
                    // 关闭通道
                    channel.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }

    }

    private void sendMassMessage(String msg, SocketChannel self) {
        logger.info("服务器消息群发中...");
        try {
            for (SelectionKey key : selector.keys()) {
                Channel targetChannel = key.channel();
                if (targetChannel instanceof SocketChannel && targetChannel != self) {
                    SocketChannel dest = (SocketChannel) targetChannel;
                    ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                    dest.write(byteBuffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }
}
