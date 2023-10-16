package com.jhc.csbot.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Optional;

/**
 * @Description: HttpHeader 处理器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
public class HttpHeaderHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest request) {
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());
            // 获取token参数以及roomId
            String token = Optional.ofNullable(urlBuilder.getQuery()).map(k -> k.get("token")).map(CharSequence::toString).orElse("");
            Long roomId = Optional.ofNullable(urlBuilder.getQuery()).map(k -> k.get("roomId")).map(CharSequence::toString).map(Long::parseLong).orElse(0L);
            NettyUtils.setAttr(ctx.channel(), NettyUtils.TOKEN, token);
            NettyUtils.setAttr(ctx.channel(), NettyUtils.ROOM_ID, roomId);
            // 设置请求路径
            request.setUri(urlBuilder.getPath().toString());
            // 移除当前handler
            ctx.pipeline().remove(this);
            ctx.fireChannelRead(request);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
