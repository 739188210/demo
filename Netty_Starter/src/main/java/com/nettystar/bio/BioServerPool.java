package com.nettystar.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: miao
 * @date 2022/4/7
 */

public class BioServerPool {
    public static void main(String[] args) {
        getBIOServerPool();
    }

    public static void getBIOServerPool() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 20, 300, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50, true), new ThreadPoolExecutor.AbortPolicy());

        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            System.out.println("server has Been Started");
            while (true) {
                Socket socket = serverSocket.accept();
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        startExchangeInfo(socket);
                    }
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startExchangeInfo(Socket socket) {
        InputStream inputStream = null;
        try {
            byte[] bytes = new byte[1024];
            inputStream = socket.getInputStream();
            StringBuilder sb = new StringBuilder();
            while (true) {
                int pos = inputStream.read(bytes);
                if (pos != -1) {
                    sb.append(new String(bytes, 0, pos));
                } else {
                    break;
                }
            }
            System.out.println("current Thread' id: " + Thread.currentThread().getId() + "---------" + sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != socket) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
