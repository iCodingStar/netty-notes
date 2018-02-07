package cn.codingstar.netty.heartbeat.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: HeartBeatServerHandler.java
 * @time: 2018/2/7 17:06
 * @software: Intellij Idea
 * @desc: 服务器端心跳检测处理器
 */
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    private int loss_connect_time = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 如果是读事件心跳检测
            if (event.state() == IdleState.READER_IDLE) {
                loss_connect_time++;
                System.out.println("5秒未收到客户端消息....");
            }
            if (loss_connect_time > 2) {
                System.out.println("关闭不活跃的channel:" + ctx.channel());
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server channel read ...");
        System.out.println(ctx.channel().remoteAddress() + " -> Server : " + msg.toString());
        ctx.channel().writeAndFlush("Heartbeat");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
