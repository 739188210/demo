package com.nettystar.netty.dubborpc.provider;

import com.nettystar.netty.dubborpc.server.NettyServer;

public class ServerStart {

    public static void main(String[] args) {
        //
        NettyServer.startServer("127.0.0.1", 9000);
    }
}
