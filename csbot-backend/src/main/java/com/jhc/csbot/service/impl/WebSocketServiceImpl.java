package com.jhc.csbot.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jhc.csbot.model.dto.ws_msg.WSMsgResp;
import com.jhc.csbot.model.enums.ws_msg.WSMsgRespTypeEnum;
import com.jhc.csbot.service.IWebSocketService;
import com.jhc.csbot.utils.JwtUtils;
import com.jhc.csbot.websocket.NettyUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: websocket 服务实现类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
@Service
@Slf4j
public class WebSocketServiceImpl implements IWebSocketService {
    // 房间号 -> channels
    // CopyOnWriteArrayList: 读写分离的list, 读不加锁, 写加锁
    private static final ConcurrentHashMap<Long, CopyOnWriteArrayList<Channel>> ROOM_CHANNEL_MAP = new ConcurrentHashMap<>();

    // 线程池执行推送消息
    private static final ExecutorService SEND_MSG_TASK_EXECUTOR = Executors.newFixedThreadPool(10);

    // 记录所有连接到用户
    private static final ConcurrentHashSet<Long> ONLINE_USER_SET = new ConcurrentHashSet<>();

    private void headShakeFail(Channel channel) {
        sendMsgByChannel(channel, WSMsgResp.builder()
                .type(WSMsgRespTypeEnum.HEAD_SHAKE_FAIL.getType())
                .data("握手失败")
                .build());
        channel.close();
    }

    @Override
    public void authorize(Channel channel) {
        // 1.得到channel中的token并进行认证
        String token = NettyUtils.getAttr(channel, NettyUtils.TOKEN);
        if (StrUtil.isBlank(token)) {
            headShakeFail(channel);
            return;
        }
        if (JwtUtils.isTokenExpired(token)) {
            headShakeFail(channel);
            return;
        }
        Long userId = JwtUtils.getUIdFromToken(token);

        // 2.认证成功后设置管道中的userId进行连接
        NettyUtils.setAttr(channel, NettyUtils.UID, userId);
        sendMsgByChannel(channel, WSMsgResp.builder()
                .type(WSMsgRespTypeEnum.HEAD_SHAKE_SUCCESS.getType())
                .data("握手成功")
                .build());
        connect(channel);
    }

    @Override
    public void connect(Channel channel) {
        Long roomId = NettyUtils.getAttr(channel, NettyUtils.ROOM_ID);
        if (!ROOM_CHANNEL_MAP.containsKey(roomId)) {
            ROOM_CHANNEL_MAP.put(roomId, new CopyOnWriteArrayList<>());
        }
        ROOM_CHANNEL_MAP.get(roomId).add(channel);
        ONLINE_USER_SET.add(NettyUtils.getAttr(channel, NettyUtils.UID));
    }

    @Override
    public void remove(Channel channel) {
        Long roomId = NettyUtils.getAttr(channel, NettyUtils.ROOM_ID);
        if (ROOM_CHANNEL_MAP.containsKey(roomId)) {
            ROOM_CHANNEL_MAP.get(roomId).remove(channel);
            ONLINE_USER_SET.remove(NettyUtils.getAttr(channel, NettyUtils.UID));
        }
    }


    private void sendMsgByChannel(Channel channel, WSMsgResp<?> wsMsgResp) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(wsMsgResp)));
    }
    @Override
    public void sendToAssignedRoom(Long roomId, WSMsgResp<?> wsMsgResp, Long skipUid) {
        if (ROOM_CHANNEL_MAP.containsKey(roomId)) {
            ROOM_CHANNEL_MAP.get(roomId).forEach(channel -> {
                Long uid = NettyUtils.getAttr(channel, NettyUtils.UID);
                if (uid != null && !uid.equals(skipUid)) {
                    SEND_MSG_TASK_EXECUTOR.execute(() -> sendMsgByChannel(channel, wsMsgResp));
                }
            });
        }
    }

    @Override
    public List<Long> getOnlineUser() {
        log.info("当前在线人数: {}", ONLINE_USER_SET.size());
        ROOM_CHANNEL_MAP.forEach((roomId, channels) -> log.info("房间号: {}, 在线人数: {}", roomId, channels.size()));
        return ONLINE_USER_SET.stream().toList();
    }

}
