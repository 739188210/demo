package com.nettystar.netty.mycodec.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 也可继承ReplayingDecoder
 * @author: miao
 * @date 2022/4/24
 */

public class ByteToLongDecoder extends ByteToMessageDecoder {
    public static final Logger logger = LoggerFactory.getLogger(ByteToLongDecoder.class);

    /**
     *  解码
     * @param ctx 上下文对象
     * @param in 入栈的byteBuf
     * @param out 传递给下个handler的数据
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
        logger.info("decode msg: {}");

    }
}
