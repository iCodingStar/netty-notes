package cn.codingstar.netty.heartbeat.idle;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: AcceptorIdleStateTrigger.java
 * @time: 2018/2/7 20:56
 * @software: Intellij Idea
 * @desc:
 */
@ChannelHandler.Sharable
public class AcceptorIdleStateTrigger extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                throw new Exception("Idle exception ...");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
