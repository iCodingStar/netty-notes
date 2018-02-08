package cn.codingstar.netty.heartbeat.idle;

import io.netty.channel.ChannelHandler;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: ChannelHandlerHolder.java
 * @time: 2018/2/7 20:42
 * @software: Intellij Idea
 * @desc: 客户端的ChannelHandler集合，由子类实现，这样做的好处：
 * 继承这个接口的子类可以很方便的获取ChannelPipeline中的Handlers
 * 获取到Handlers之后方便ChannelPipeline中的Handler的初始化，
 * 在重连的时候，也能很方便的获取所有的handlers
 */
public interface ChannelHandlerHolder {

    ChannelHandler[] handlers();

}
