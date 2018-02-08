package cn.codingstar.netty.heartbeat.idle;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

import java.util.concurrent.TimeUnit;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: HeartBeatClient.java
 * @time: 2018/2/7 21:10
 * @software: Intellij Idea
 * @desc:
 */
public class HeartBeatClient {

    protected final HashedWheelTimer timer = new HashedWheelTimer();

    private Bootstrap bootstrap;

    private final String host;

    private final int port;

    private final ConnectorIdleStateTrigger idleStateTrigger = new ConnectorIdleStateTrigger();


    public HeartBeatClient(final String host, final int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO));

        final ConnectionWatchDog watchDog = new ConnectionWatchDog(bootstrap, timer, host, port, true) {
            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{
                        this,
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        idleStateTrigger,
                        new StringDecoder(),
                        new StringEncoder(),
                        new HeartBeatClientHandler()
                };
            }
        };

        ChannelFuture future;
        try {
            synchronized (bootstrap) {
                // 进行连接
                bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(watchDog.handlers());
                    }
                });

                future = bootstrap.connect(host, port);
            }
            // 以下代码在同步代码块外面是安全的
            future.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new Exception("connects to  fails", e);
        }
    }

    public static void main(String[] args) throws Exception {
        HeartBeatClient client = new HeartBeatClient("localhost", 8080);
        client.start();
    }
}
