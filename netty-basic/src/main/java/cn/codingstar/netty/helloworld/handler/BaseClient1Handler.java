package cn.codingstar.netty.helloworld.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: BaseClient1Handler.java
 * @time: 2018/2/6 22:14
 * @software: Intellij Idea
 * @desc:
 */
public class BaseClient1Handler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("BaseClient1Handler channelActive ...");
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("BaseClient1Handler channelInActive ...");
    }
}
