package com.nettystar.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author: miao
 * @date 2022/4/11
 */

public class GroupChatClient {
    private static final Logger logger = LoggerFactory.getLogger(GroupChatClient.class);

    private Selector selector;

    private SocketChannel socketChannel;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));

            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_READ);

            logger.info("{} is ok....", socketChannel.getRemoteAddress().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendInfo() {
      //  socketChannel.write(ByteBuffer.wrap());
    }

    public void readInfo() {
        try {
            int channelCount = selector.select();
            if (channelCount > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                        socketChannel.read(byteBuffer);

                        logger.info("客户端读取数据：{} ", new String(byteBuffer.array()));
                    }
                    iterator.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
