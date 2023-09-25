<template>
    <div class="index_container">
        <div class="wrapper">
            <Sidebar />
            <ChatBox />
        </div>
    </div>
    <Logout />
</template>

<script setup lang="ts">
import Sidebar from "@/views/index/components/sidebar.vue";
import Logout from "@/views/index/components/logout.vue";
import ChatBox from "@/views/index/components/chat-box/index.vue";
</script>

<style scoped lang="scss">
.index_container {
    height: 100vh;
    width: 100vw;
    // 背景图片, cover
    background-image: url(../../assets/images/index_background.jpg);
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
    display: flex;
    align-items: center;
    justify-content: center;
    .wrapper {
        background-color: #272A37;
        width: 80vw;
        height: 80vh;
        display: flex;
        border-radius: 16px;
    }
}
</style>