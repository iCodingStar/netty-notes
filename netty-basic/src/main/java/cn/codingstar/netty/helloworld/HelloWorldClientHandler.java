package cn.codingstar.netty.helloworld;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: HelloWorldClientHandler.java
 * @time: 2018/2/6 21:12
 * @software: Intellij Idea
 * @desc:
 */
public class HelloWorldClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("I am client ...");
        System.out.println("HelloWorldClientHandler active ...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("HelloWorldClientHandler read Message : " + msg.toString());
        ctx.writeAndFlush("Hello , I am client .");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
