package com.nettystar.netty.dubborpc.customer;

import com.nettystar.netty.dubborpc.client.NettyClient;
import com.nettystar.netty.dubborpc.inter.HelloService;

public class CustomerBootStrap {
    // 约定协议头
    public static final String providerName = "HelloService#Hello#";

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        // 创建代理对象
        HelloService service = (HelloService) nettyClient.getBean(HelloService.class, providerName);

        // 通过代理对象调用方法

        String result = service.hello("你好服务器。");

        System.out.println(result);
    }
}
