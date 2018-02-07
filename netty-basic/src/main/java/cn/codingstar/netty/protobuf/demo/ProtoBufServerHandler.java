package cn.codingstar.netty.protobuf.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: ProtoBufServerHandler.java
 * @time: 2018/2/7 11:44
 * @software: Intellij Idea
 * @desc:
 */
public class ProtoBufServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RichManProto.RichMan richMan = (RichManProto.RichMan) msg;
        System.out.println(richMan.getName() + "有" + richMan.getCarsCount() + "量车");
        List<RichManProto.RichMan.Car> cars = richMan.getCarsList();
        if (cars != null) {
            for (RichManProto.RichMan.Car car : cars) {
                System.out.println(car.getName());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
