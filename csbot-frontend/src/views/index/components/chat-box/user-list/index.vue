<template>
    <div class="user_list">
        <div class="header">
            咨询人数：{{userList.length}}
        </div>
        <div class="content">
            <a-list :bordered="false" :split="false" scrollbar :max-height="maxHeight">
                <div style="height:50px" v-for="(user, index) in userList" :key="index">
                    <SingleUser :user="user"/>
                </div>
            </a-list>
        </div>
    </div>
</template>

<script setup lang="ts">
import {onMounted, ref} from "vue";
import SingleUser from "@/views/index/components/chat-box/user-list/single-user/index.vue";
import {GetOnlineUserListResponse, UserInfo} from "@/api/user/type.ts";
import useUserStore from "@/pinia/modules/user";
import {reqOnlineUserList} from "@/api/user";
import {Message} from "@arco-design/web-vue";

// 用户列表
const userList = ref<UserInfo[]>([])
const userStore = useUserStore()
// 列表的最大高度
const maxHeight = ref<number>(0)
onMounted(async () => {
    const content = document.querySelector('.content')
    maxHeight.value = content?.clientHeight as number
    userList.value.push(userStore.userInfo)
    const resp: GetOnlineUserListResponse = await reqOnlineUserList()
    if (resp.statusCode != 0) {
        Message.error(resp.statusMsg)
    }
    userList.value = userList.value.concat(resp.data)
})
</script>

<style scoped lang="scss">
.user_list {
    width: 180px;
    height: 100%;
    background-color: #323644;
    border-radius: 8px;
    padding: 12px;
    display: flex;
    flex-direction: column;
    .header {
        padding-bottom: 8px;
        color: white;
        font-size: 15px;
    }
    .content {
        margin-top: 8px;
        flex: 1;
        overflow-y: hidden;
    }
}
</style>