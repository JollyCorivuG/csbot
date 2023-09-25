<template>
    <div class="chat_box">
        <ChatMessage />
        <UserList />
    </div>
</template>

<script setup lang="ts">
import ChatMessage from "@/views/index/components/chat-box/chat-message/index.vue";
import UserList from "@/views/index/components/chat-box/user-list/index.vue";
</script>

<style scoped lang="scss">
.chat_box {
    height: 100%;
    padding: 20px 20px 20px 0;
    display: flex;
    flex: 1;
}
</style>