import request from "@/utils/request.ts";
import {SendMsgParams, SendMsgResponse} from "@/api/msg/type.ts";


enum MsgAPI {
    // 发送信息
    sendMsgUrl = '/msg/send',
}

// 发送信息
export const reqSendMsg = (params: SendMsgParams) => request.post<any, SendMsgResponse>(MsgAPI.sendMsgUrl, params)


