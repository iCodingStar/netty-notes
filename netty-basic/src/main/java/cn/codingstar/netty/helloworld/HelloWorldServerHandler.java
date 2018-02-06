package cn.codingstar.netty.helloworld;

import io.netty.channel.*;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: HelloWorldServerHandler.java
 * @time: 2018/2/6 20:51
 * @software: Intellij Idea
 * @desc: 服务端通道用于处理输入数据的Handler
 */
public class HelloWorldServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server channel read ...");
        System.out.println(ctx.channel().remoteAddress() + " -> Server : " + msg.toString());
        ctx.writeAndFlush("Server write : Hello , I am Server !").addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
