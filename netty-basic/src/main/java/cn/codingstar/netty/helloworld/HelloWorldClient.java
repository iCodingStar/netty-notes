package cn.codingstar.netty.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: HelloWorldClient.java
 * @time: 2018/2/6 21:14
 * @software: Intellij Idea
 * @desc:
 */
public class HelloWorldClient {

    private final String host;

    private final int port;

    public HelloWorldClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new HelloWorldClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        HelloWorldClient client = new HelloWorldClient("127.0.0.1",8090);
        client.start();
    }
}
