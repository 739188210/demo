package com.nettystar.netty.mycodec.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: miao
 * @date 2022/4/24
 */

public class LongToByteEncoder extends MessageToByteEncoder<Long> {
    public static final Logger logger = LoggerFactory.getLogger(LongToByteEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        out.writeLong(msg);
        logger.info("encode msg: {}", msg);
      //  ctx.fireChannelRead(out);
    }
}
