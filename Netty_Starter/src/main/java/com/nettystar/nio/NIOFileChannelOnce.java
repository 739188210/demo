package com.nettystar.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author: miao
 * @date 2022/4/8
 */

public class NIOFileChannelOnce {
    public static void main(String[] args) {
//        writeNIOFileChannel();
//        readNIOFileChannel();
//        readToWriteAnotherFile();
//        writeFileWithoutBuffer();

        MappedByteBufferTest();
    }


    public static void writeNIOFileChannel() {
        String str = "hello NIO Program\n\r sdkosd\n\r 你是奥数\n\r 是地级市 ";
        FileOutputStream fileOutputStream = null;
        try {
            // 创建一个文件输出流 -> 获取对应fileChannel
            String filePath = "E://javaOutPut/filechannel01.txt";
            File path = new File(filePath.substring(0, filePath.lastIndexOf("/")));
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            FileChannel channel = fileOutputStream.getChannel();

            // 创建一个缓冲区 用于数据传递
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 将str 放入 buffer
            byteBuffer.put(str.getBytes(StandardCharsets.UTF_8));
            // 反转buffer， 接下来将buffer中数据读取出来，写入channel
            byteBuffer.flip();
            // 将buffer中数据写入channel，对channel 来说是写
            channel.write(byteBuffer);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void readNIOFileChannel() {
        FileInputStream fileInputStream = null;
        String filePath = "E://javaOutPut/filechannel01.txt";
        try {
            fileInputStream = new FileInputStream(filePath);
            FileChannel channel = fileInputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 读取channel中的数据，写入buffer
            channel.read(byteBuffer);

            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.limit()];
            byteBuffer.get(bytes);
            System.out.println(new String(bytes));


            //     System.out.println(new String(byteBuffer.array()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从一个文件读取数据写入到另一个文件
     * 两个channel 一个buffer
     */
    public static void readToWriteAnotherFile() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream("E://javaOutPut/filechannel01.txt");
            FileChannel inputChannel = fileInputStream.getChannel();

            fileOutputStream = new FileOutputStream("E://javaOutPut/filechannel02.txt");
            FileChannel outputChannel = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while(true) {
                byteBuffer.clear();
                int read = inputChannel.read(byteBuffer);
                if (read == -1) {
                    break;
                }
                byteBuffer.flip();
                outputChannel.write(byteBuffer);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * use Channel.transferFrom() to copy a file
     */
    public static void writeFileWithoutBuffer() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream("E://javaOutPut/1.jpg");
            FileChannel inputChannel = fileInputStream.getChannel();

            fileOutputStream = new FileOutputStream("E://javaOutPut/2.jpg");
            FileChannel outputChannel = fileOutputStream.getChannel();

            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void MappedByteBufferTest() {
        RandomAccessFile fileInputStream = null;
        String filePath = "E://javaOutPut/filechannel01.txt";
        try {
            fileInputStream = new RandomAccessFile(filePath, "rw");
            FileChannel inputChannel = fileInputStream.getChannel();
            System.out.println(inputChannel.isOpen());

            MappedByteBuffer map = inputChannel.map(FileChannel.MapMode.READ_WRITE, 2, 5);

            System.out.println(inputChannel.size());
            map.put(0,  (byte) '6');

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
