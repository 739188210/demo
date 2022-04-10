package com.nettystar.nio;

import java.nio.ByteBuffer;

/**
 * @author: miao
 * @date 2022/4/8
 */

public class TestByteBuffer {

    public static void main(String[] args) {
        // 创建直接缓冲区, 开辟的是系统资源 ，未设置buffer.limit 时， limit == capacity
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        // 设置缓冲区的初始容量， 开辟的是jvm资源
        ByteBuffer.allocate(1024);
        //
        byte[] bytes = {'1', '1', '2', '2'};
        // 包装一个已有的数组, 将不会在堆上创建新的数组，而是直接利用 bytes 做backing store，
        // 根据数组初始化buffer， 并赋值postion = offset， limit = offset + length
        // 也就是说 offset length 只是用于初始化postion limit ，buffer可以操作整个数组 ，要注意flip()后postion值
        ByteBuffer buffer = ByteBuffer.wrap(bytes, 1, 2);
        int index = 2;
        buffer.put((byte) 98);
        // 从index处赋值
        buffer.put(index, (byte) 65);
        // 读写操作转化，反转此缓冲区，将limit设为当前位置(position), 将position设为0，丢弃mark
        buffer.flip();
        // 再次读/写，limit保持不变，将position设置为 0 ,丢弃mark
        buffer.rewind();

        // 返回当前位置与限制之间的元素数，即还未读出的字节数。
        int byteNums = buffer.remaining();
        // 告知在当前位置和限制之间是否有元素。
        while (buffer.hasRemaining()) {
            // 从当前位置(postion)处get， position自动+1
            System.out.println(buffer.get());
        }
        // 将position设置为 0，将limit设置为容量，并丢弃mark。 但 数据未清除！！！
        buffer.clear();
        // 在当前位置设标记
        buffer.mark();
        // 根据mark 回滚position位置
        buffer.reset();
        // 判断是否具有可写的底层实现数组 。
        boolean hasArray = buffer.hasArray();
        // 返回底层实现数组
        byte[] array = buffer.array();

        // 浅拷贝
        buffer.duplicate();
        // 浅拷贝一个共享当前buffer的新的buffer对象
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        // 浅拷贝一个共享从当前buffer的当前位置开始的 新的buffer对象
        buffer.slice();

    }
}
