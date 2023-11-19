import {MsgInfo} from "@/api/msg/type.ts";

export interface MsgState {
    msgList: MsgInfo[],
    scrollerFlag: 0 | 1
}