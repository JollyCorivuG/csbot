import {defineStore} from "pinia";
import {MsgState} from "@/pinia/modules/msg/type.ts";

const useMsgStore = defineStore('Msg', {
    state: (): MsgState => {
        return {
            msgList: []
        }
    },
    getters: {

    },
    actions: {

    }
})

export default useMsgStore