import useMsgStore from "@/pinia/modules/msg";
import {WSMsgReq, WSMsgReqType, WSMsgResp, WSMsgRespType} from "@/utils/websocket/type.ts";
import {Message} from "@arco-design/web-vue";
import {MsgInfo} from "@/api/msg/type.ts";

const msgStore = useMsgStore()
export class WS {
    private wsClient: WebSocket // websocket客户端
    private heartbeatTimer: NodeJS.Timeout // 心跳检测定时器
    constructor(url: string) {
        this.wsClient = new WebSocket(url)
        this.heartbeatTimer = setTimeout(() => {}, 0)
        // 收到消息
        this.wsClient.addEventListener('message', this.onReceiveMsg)
        // 建立链接
        this.wsClient.addEventListener('open', this.onConnectOpen)
        // 关闭连接
        this.wsClient.addEventListener('close', this.onConnectClose)
        // 连接错误
        this.wsClient.addEventListener('error', this.onConnectError)
    }

    // 收到消息
    private onReceiveMsg = (event: MessageEvent): void => {
        const msg: WSMsgResp = JSON.parse(event.data as string)
        if (msg.type == WSMsgRespType.HEAD_SHAKE_FAIL) {
            Message.error('websocket握手失败')
        } else if (msg.type == WSMsgRespType.HEAD_SHAKE_SUCCESS) {
            // 在握手成功后, 开启定时器, 每隔10s向服务器发送一次心跳检测
            this.heartbeatTimer = setInterval(() => {
                this.sendMsg({type: WSMsgReqType.HEARTBEAT, data: '心跳检测!'})
            }, 10000)
        } else if (msg.type == WSMsgRespType.MESSAGE) {
            const resp: WSMsgResp = JSON.parse(event.data as string) as WSMsgResp
            const receiveMsg: MsgInfo = resp.data as MsgInfo
            receiveMsg.sendTime = new Date(receiveMsg.sendTime)
            msgStore.msgList.push(receiveMsg)
            msgStore.scrollerFlag = msgStore.scrollerFlag == 0 ? 1 : 0
            console.log(msgStore.msgList)
        }
    }

    // 建立连接
    private onConnectOpen = (event: Event): void => {
        console.log('建立连接' + event)
    }

    // 关闭连接
    private onConnectClose = (event: CloseEvent): void => {
        console.log('关闭连接' + event)
        if (this.heartbeatTimer) {
            clearInterval(this.heartbeatTimer)
        }
    }

    // 连接错误
    private onConnectError = (event: Event): void => {
        Message.error('websocket 连接错误!' + event)
    }

    // 发送消息 (暴露给外部的方法)
    public sendMsg = (msg: WSMsgReq): void => {
        if (this.wsClient.readyState === 1) {
            this.wsClient.send(JSON.stringify(msg))
        }
    }
}