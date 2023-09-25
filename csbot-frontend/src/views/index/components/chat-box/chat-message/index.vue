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
                <a-list :bordered="false" :split="false" scrollbar :max-height="maxChatViewHeight">
                    <div v-for="i in 100" class="single_message_container">
                        <div v-if="i % 2 == 0" style="justify-self: flex-start">
                            {{i}}
                        </div>
                        <div v-else style="justify-self: flex-end">
                            {{i}}
                        </div>
                    </div>

                </a-list>
            </div>
            <div class="input">
                <MsgInput />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import MsgInput from "@/views/index/components/chat-box/chat-message/msg-input/index.vue";
import {onMounted, ref} from "vue";

// 聊天框的最大高度
const maxChatViewHeight = ref<number>(0)
onMounted(() => {
    const messageContainer = document.querySelector('.message_container')
    maxChatViewHeight.value = messageContainer?.clientHeight as number - 8
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
                margin: 8px 16px;
                display: flex;
                color: white;
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