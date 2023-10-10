// 通用响应信息
import {UserInfo} from "@/api/user/type.ts";

export interface CommonResponse {
    statusCode: number,
    statusMsg: string
}

// 消息类型
export enum MsgType {
    TEXT = 0,
    IMG = 1,
    FILE = 2
}

// 文本消息
export interface TextMsg {
    content: string
}

// 返回的信息信息
export interface MsgInfo {
    id: number,
    senderInfo: UserInfo,
    type: number
    body: TextMsg
    sendTime: Date
}

// 发送信息请求参数
export interface SendMsgParams {
    roomId: number,
    type: number,
    body: TextMsg
}

// 发送信息返回响应
export interface SendMsgResponse extends CommonResponse {
    data: MsgInfo
}




