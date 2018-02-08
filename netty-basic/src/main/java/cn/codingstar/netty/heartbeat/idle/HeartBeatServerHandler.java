package cn.codingstar.netty.heartbeat.idle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: HeartBeatServerHandler.java
 * @time: 2018/2/7 21:05
 * @software: Intellij Idea
 * @desc:
 */
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("Server channelRead ...");
        System.out.println(ctx.channel().remoteAddress() + " -> Server : " + msg.toString());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
