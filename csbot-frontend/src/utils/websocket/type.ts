// 向 ws 服务器发送的消息类型
export enum WSMsgReqType {
    HEARTBEAT = 0, // 心跳检验
    AUTHORIZE = 1 // 握手认证
}
export interface WSMsgReq {
    type: WSMsgReqType,
    data: string
}

// 接收 ws 服务器发来的消息类型
export enum WSMsgRespType {
    MESSAGE = 0, // 新消息
    ONLINE_OFFLINE_NOTIFY = 1, // 上下线通知
    HEAD_SHAKE_SUCCESS = 2, // 握手成功
    HEAD_SHAKE_FAIL = 3 // 握手失败
}
export interface WSMsgResp {
    type: WSMsgRespType,
    data: object
}
