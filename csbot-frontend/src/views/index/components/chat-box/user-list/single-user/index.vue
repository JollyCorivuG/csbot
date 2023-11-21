<template>
    <div class="single_user">
        <a-avatar :size="28">
            <img
                alt="avatar"
                :src="user.avatar"
                style="width: 28px;height: 28px;"
            />
            <template #trigger-icon>
                <a-badge status="success" :dot-style="{
                    height: '7.2px',
                    width: '7.2px'
                }" color="#B8FE17" />
            </template>
        </a-avatar>
        <div class="name">
            {{user.nickName}}
        </div>
    </div>
</template>

<script setup lang="ts">
import useUserStore from "@/pinia/modules/user";

const userStore = useUserStore()
// 接收父亲组件传递的数据['friend'], 类型是UserInfo
import {PropType} from "vue";
import {UserInfo} from "@/api/user/type.ts";
defineProps({
    user: {
        type: Object as PropType<UserInfo>,
        required: true
    }
})
</script>

<style scoped lang="scss">
.single_user {
    &:hover {
        background-color: #5B5E69;
    }
    display: flex;
    align-items: center;
    padding: 5px 0;
    .name {
        margin-left: 8px;
        color: white;
        font-size: 15px;
        white-space: nowrap;
    }
}
::v-deep(.arco-avatar-trigger-icon-button) {
    background: none;
}
</style>