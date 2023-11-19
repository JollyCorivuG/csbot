<template>
    <div class="chat_message">
        <div class="message_list">
            <div class="message_item active">
                <img
                    alt="logo"
                    src="/logo.svg"
                />
                <div class="item_info">
                    <div class="name">
                        <span>客服</span>
                        <div class="tag">
                            机器人
                        </div>
                    </div>
                    <div class="latest_message">
                        Bot: hello
                    </div>
                </div>
            </div>
        </div>
        <div class="chat_container">
            <div class="message_container">
                <a-scrollbar :style="scrollBarStyle" ref="messagePanel">
                    <div class="total" style="scroll-behavior: smooth">
                        <div class="single_message_container" v-for="(msg, index) in msgStore.msgList" :key="index">
                            <div class="send-time-container" v-if="index == 0 || !isInSameMinute(msgStore.msgList[index - 1].sendTime.toString(), msgStore.msgList[index].sendTime.toString())">
                                {{format(msg.sendTime.toString())}}
                            </div>
                            <div class="other-people-message" v-if="msg.senderInfo.id != userStore.userInfo.id">
                                <img alt="avatar" src="/logo.svg" />
                                <div class="chat_item_box">
                                    <div class="name">Bot</div>
                                    <div class="content">{{msg.body.content}}</div>
                                </div>
                            </div>
                            <div class="self-message" v-else>
                                <div class="chat_item_box">
                                    <div class="name">{{msg.senderInfo.nickName}}</div>
                                    <div class="content">{{msg.body.content}}</div>
                                </div>
                                <img alt="avatar" :src="msg.senderInfo.avatar" />
                            </div>
                        </div>
                    </div>
                </a-scrollbar>
            </div>
            <div class="input">
                <MsgInput />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import MsgInput from "@/views/index/components/chat-box/chat-message/msg-input/index.vue";
import {nextTick, onMounted, ref, watch} from "vue";
import useMsgStore from "@/pinia/modules/msg";
import useUserStore from "@/pinia/modules/user";
import {format, isInSameMinute} from "@/utils/time.ts";
import {ScrollbarInstance} from "@arco-design/web-vue";

// 聊天框的最大高度
const scrollBarStyle = ref<object>(
    {
        height: '0px',
        overflow: 'auto'
    }
)
const maxChatViewHeight = ref<number>(0)
onMounted(() => {
    const messageContainer = document.querySelector('.message_container')
    maxChatViewHeight.value = messageContainer?.clientHeight as number - 8
    scrollBarStyle.value = {
        height: maxChatViewHeight.value + 'px',
        overflow: 'auto'
    }
})

// 消息列表
const msgStore = useMsgStore()
const userStore = useUserStore()

// 监视 msgStore.scrollerFlag 的变化, 如果发生变化就滚动到底部
const messagePanel = ref<ScrollbarInstance>()
watch(() => msgStore.scrollerFlag, async (newVal, oldVal) => {
    if (newVal != oldVal) {
        await nextTick(() => {
            messagePanel.value?.scrollTop(1000000000)
        })
    }
})
</script>

<style scoped lang="scss">
.chat_message {
    margin-right: 20px;
    flex: 1;
    height: 100%;
    display: flex;
    .message_list {
        margin-left: 10px;
        width: 180px;
        .message_item {
            height: 60px;
            background-color: #323644;
            padding: 12px 10px;
            img {
                width: 38px;
                height: 38px;
            }
            cursor: pointer;
            border-radius: 8px;
            display: flex;
            align-items: center;
            .item_info {
                display: flex;
                flex-direction: column;
                margin-left: 10px;
                justify-content: space-evenly;
                width: 100%;
                .name {
                    color: white;
                    font-size: 15px;
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    .tag {
                        background-color: #EFE2BB;
                        color: #c59512;
                        font-weight: 700;
                        font-size: 12px;
                        padding: 3px 4px;
                        margin-right: 5px;
                        margin-bottom: 2px;
                        border-radius: 4px;
                    }
                }
                .latest_message {
                    color: rgba(255, 255, 255, 0.6);
                    font-size: 12px;
                    margin-top: 5px;
                }
            }
        }
        // 含有类active的背景颜色为#2C3E50
        .active {
            background-color: #2C3E50;
        }
    }
    .chat_container {
        background-color: #323644;
        flex: 1;
        display: flex;
        flex-direction: column;
        height: 100%;
        border-radius: 8px;
        margin-left: 18px;
        padding-bottom: 16px;
        .message_container {
            flex: 1;
            max-height: calc(100% - 40px);
            .single_message_container {
                margin: 20px 16px;
                display: flex;
                flex-direction: column;
                color: white;
                img {
                    width: 40px;
                    height: 40px;
                }
                .other-people-message {
                    width: 100%;
                    display: flex;
                    align-items: flex-start;
                    .chat_item_box {
                        flex: 1;
                        padding: 0 52px 0 12px;
                        display: flex;
                        flex-direction: column;
                        align-items: flex-start;
                        .name {
                            margin-bottom: 4px;
                            font-size: 14px;
                            color: #999;
                        }
                        .content {
                            display: inline-block;
                            min-height: 1em;
                            max-width: 100%;
                            padding: 8px 12px;
                            font-size: 15px;
                            line-height: 22px;
                            word-break: break-word;
                            background-color: #383C4B;
                            border-radius: 2px 18px 18px;
                        }
                    }
                }
                .self-message {
                    display: flex;
                    width: 100%;
                    align-items: flex-start;
                    .chat_item_box {
                        display: flex;
                        flex-direction: column;
                        align-items: flex-end;
                        flex: 1;
                        padding: 0 12px 0 52px;
                        .name {
                            margin-bottom: 4px;
                            font-size: 14px;
                            color: #999;
                        }
                        .content {
                            display: inline-block;
                            max-width: 100%;
                            min-height: 1em;
                            padding: 8px 12px;
                            font-size: 15px;
                            line-height: 22px;
                            word-break: break-word;
                            background-color: #3991F7;
                            border-radius: 18px 2px 18px 18px;
                        }
                    }
                }
                .send-time-container {
                    margin: 8px 16px;
                    display: flex;
                    color: #999;
                    font-size: 14px;
                    align-items: center;
                    justify-content: center;
                    width: 100%;
                }
            }

        }
        .input {
            padding: 2px 6px;
            min-height: 40px;
            border-radius: 12px;
            background-color: #424656;
            margin: 0 16px;
            display: flex;
            align-items: center;
        }
    }
}
</style>