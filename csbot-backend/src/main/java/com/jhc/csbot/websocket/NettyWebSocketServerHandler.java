package com.jhc.csbot.websocket;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.jhc.csbot.model.dto.ws_msg.WSMsgReq;
import com.jhc.csbot.model.enums.ws_msg.WSMsgReqTypeEnum;
import com.jhc.csbot.service.IWebSocketService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: Netty WebSocket 服务端处理器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
@Slf4j
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final IWebSocketService webSocketService = SpringUtil.getBean(IWebSocketService.class);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.info("客户端尝试连接");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        webSocketService.remove(ctx.channel());
        webSocketService.getOnlineUser();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.warn("触发 channelInactive 掉线![{}]", ctx.channel().id());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent idleStateEvent) {
            // 如果处于读空闲状态，则关闭连接
            if (idleStateEvent.state() == IdleStateEvent.READER_IDLE_STATE_EVENT.state()) {
                webSocketService.remove(ctx.channel());
                ctx.channel().close();
                webSocketService.getOnlineUser();
            }
        } else if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            // 握手成功后, 需要进行认证
            webSocketService.authorize(ctx.channel());
            webSocketService.getOnlineUser();
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        WSMsgReq req = JSONUtil.toBean(msg.text(), WSMsgReq.class);
        WSMsgReqTypeEnum msgType = WSMsgReqTypeEnum.of(req.getType());
        switch (msgType) {
            case HEARTBEAT -> {
            }
            case AUTHORIZE -> log.info("收到认证包");
            default -> log.info("收到未知类型的包");
        }
    }
}
