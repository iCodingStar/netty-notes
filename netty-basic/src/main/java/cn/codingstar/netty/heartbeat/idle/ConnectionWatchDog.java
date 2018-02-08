package cn.codingstar.netty.heartbeat.idle;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: ConnectionWatchDog.java
 * @time: 2018/2/7 21:15
 * @software: Intellij Idea
 * @desc: 重连检查狗，当发现链路不稳定关闭后，进行12次重连
 */
@ChannelHandler.Sharable
public class ConnectionWatchDog extends ChannelInboundHandlerAdapter implements TimerTask, ChannelHandlerHolder {

    private final Bootstrap bootstrap;

    private final Timer timer;

    private final String host;

    private final int port;

    private int attempts;

    private volatile boolean reconnect = true;

    public ConnectionWatchDog(Bootstrap bootstrap, Timer timer, String host, int port, boolean reconnect) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.host = host;
        this.port = port;
        this.reconnect = reconnect;
    }

    /**
     * Channel每次active的时候将其attempts置为0
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("当前链路已经复活，重连次数置为0 _^^_ ");
        attempts = 0;
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接关闭");
        if (reconnect) {
            System.out.println("链接关闭，将重新链接...");
            if (attempts < 12) {
                attempts++;
                // 重连时间会越来越长
                int timeout = 2 << attempts;
                timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
            }
        }
        ctx.fireChannelInactive();
    }

    public ChannelHandler[] handlers() {
        return new ChannelHandler[0];
    }

    public void run(Timeout timeout) throws Exception {
        ChannelFuture future;
        // bootstrap已经初始化好了，只需要将handler填入即可
        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(handlers());
                }
            });
            /**
             * 重新连接的时候不可以同步阻塞？
             */
            future = bootstrap.connect(host, port);
            System.out.println();
        }
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                boolean succeed = future.isSuccess();
                // 如果重连失败，则调用channelInactive方法，再次触发重连事件，一直尝试12次，如果失败，则不重连
                if (!succeed) {
                    System.out.println("重连失败");
                    future.channel().pipeline().fireChannelInactive();
                } else {
                    System.out.println("重连成功");
                }
            }
        });
    }
}
