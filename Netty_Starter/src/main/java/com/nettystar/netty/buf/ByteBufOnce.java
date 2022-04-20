package com.nettystar.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Unpooled: netty专门用来操作缓冲区 的工具类
 * @author: miao
 * @date 2022/4/20
 */

public class ByteBufOnce {
    public static void main(String[] args) {
   //     bufFirst();
        bufSecond();
    }

    public static  void bufFirst() {
        ByteBuf byteBuf = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            byteBuf.writeByte(i);
        }

        for (int i= 0; i < byteBuf.capacity(); i ++) {
            System.out.println(byteBuf.readByte());
        }

    }

    public static void bufSecond() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", StandardCharsets.UTF_8);

        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();

            System.out.println(new String(array, StandardCharsets.UTF_8));

            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            byteBuf.getByte(1);
            byteBuf.readByte();
            System.out.println(byteBuf.arrayOffset());

            System.out.println(byteBuf.readerIndex());



        }
    }
}
