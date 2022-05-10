package com.nettystar.netty.dubborpc.provider;

import com.nettystar.netty.dubborpc.inter.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class HelloServiceImpl implements HelloService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String hello(String msg) {
        LOGGER.info("client send msg: {}", msg);
        if (StringUtils.hasText(msg)) {
            return "sever has received msg: " + msg;
        } else {
            return "sever has received msg";
        }
    }
}
