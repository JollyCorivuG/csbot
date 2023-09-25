<template>
    <div class="logout_container">
        <div class="logout" @click="logout">
            <icon-import />
            <span>退出登录</span>
        </div>
    </div>
</template>

<script setup lang="ts">
import useUserStore from "@/pinia/modules/user";
import {useRouter} from "vue-router";
import {Message} from "@arco-design/web-vue";

// 退出登录
const userStore = useUserStore()
const router = useRouter()
const logout = () => {
    userStore.clearData()
    router.push('/login')
    Message.info('退出登录!')
}
</script>

<style scoped lang="scss">
.logout_container {
    // 脱离文档流, 位于右上角
    position: absolute;
    top: 0;
    right: 0;
    padding: 15px;
    .logout {
        cursor: pointer;
        &:hover {
            color: #3491FA;
        }
        color: rgba(255, 255, 255, 0.6);
        display: flex;
        align-items: center;
        span {
            margin-left: 3px;
        }
    }
}
</style>