package com.nettystar.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 *
 * Scattering :将数据依次写入到buffer数组 分散
 * Gathering :从buffer数组依次读取数据
 *
 * @author: miao
 * @date 2022/4/11
 */

public class ScatteringAndGathering {
    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;
        try {
            // 打开serverSocket端口通道并绑定到端口6666
            serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
            serverSocketChannel.socket().bind(inetSocketAddress);

            ByteBuffer[] byteBuffers = new ByteBuffer[2];
            byteBuffers[0] = ByteBuffer.allocate(5);
            byteBuffers[1] = ByteBuffer.allocate(3);
            // 获取对应的socketChannel
            SocketChannel socketChannel = serverSocketChannel.accept();

            int messageLength = 8;
            while (true) {
                // channel将读取到的数据写入到buffers
                int readCount = 0;
                while (readCount < messageLength) {
                    long tempReadCount = socketChannel.read(byteBuffers);
                    readCount += tempReadCount;
                    Arrays.asList(byteBuffers).stream().map(buffer -> {
                        return "buffer's position: " + buffer.position() + " buffer's limit: " + buffer.limit();
                    }).forEach(System.out::println);
                }

                Arrays.asList(byteBuffers).forEach(Buffer::flip);

                int writeCount = 0;
                while (writeCount < messageLength) {
                    long tempWriteCount = socketChannel.write(byteBuffers);
                    writeCount += tempWriteCount;

                }

                Arrays.asList(byteBuffers).forEach(Buffer::clear);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocketChannel != null) {
                    serverSocketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
