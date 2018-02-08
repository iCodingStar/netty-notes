package cn.codingstar.netty.heartbeat.idle;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: HeartBeatClientHandler.java
 * @time: 2018/2/7 21:13
 * @software: Intellij Idea
 * @desc:
 */
@ChannelHandler.Sharable
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("激活时间 ：" + new Date());
        System.out.println("HeartBeatClientHandler channelActive ...");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        System.out.println(message);
        if (message.equals("HeartBeat")) {
            ctx.writeAndFlush("has read message from server ...");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("停止时间 ：" + new Date());
        System.out.println("HeartBeatClientHandler channelInactive");
    }
}
