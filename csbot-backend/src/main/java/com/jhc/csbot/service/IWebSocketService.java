package com.jhc.csbot.service;

import com.jhc.csbot.model.dto.ws_msg.WSMsgResp;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @Description: websocket 服务接口
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
public interface IWebSocketService {
    /**
     * 认证
     * @param channel
     */
    void authorize(Channel channel);

    /**
     * 连接
     * @param channel
     */
    void connect(Channel channel);

    /**
     * 断开连接
     * @param channel
     */
    void remove(Channel channel);

    /**
     * 发送消息给指定房间
     * @param roomId
     * @param wsMsgResp
     * @param skipUid
     */
    void sendToAssignedRoom(Long roomId, WSMsgResp<?> wsMsgResp, Long skipUid);

    /**
     * 显示在线用户
     * @return
     */
    List<Long> getOnlineUser();
}
