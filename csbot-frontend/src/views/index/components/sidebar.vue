<template>
    <div class="sidebar">
        <a-avatar :size="50">
            <img alt="avatar" :src="userStore.userInfo.avatar" />
        </a-avatar>
        <div class="tool">
            <a-trigger position="right" auto-fit-position :unmount-on-close="false" show-arrow>
                <div class="weixin">
                    <icon-wechat size="30" ></icon-wechat>
                    <span>微信</span>
                </div>
                <template #content>
                    <a-image
                        width="180"
                        :src="weixinImage"
                        :preview="false"
                    />
                </template>
            </a-trigger>
            <div class="git" @click="goProOri">
                <icon-github size="30"/>
                <span>项目源码</span>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import useUserStore from "@/pinia/modules/user";
import weixinImage from '@/assets/images/weixin.png';

// 用户信息
const userStore = useUserStore();

// 跳转到项目源码
const goProOri = () => {
    window.open("https://github.com/JollyCorivuG/csbot")
}
</script>

<style scoped lang="scss">

.sidebar {
    .arco-trigger-content {
        background-color: #272A37;
    }
    height: 100%;
    width: 80px;
    padding: 20px 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    .weixin {
        display: flex;
        flex-direction: column;
        align-items: center;
        color: white;
        cursor: pointer;
        span {
            margin-top: 2px;
        }
    }
    .git {
        margin-top: 25px;
        display: flex;
        flex-direction: column;
        align-items: center;
        color: white;
        cursor: pointer;
        // 当鼠标经过字体时, 改变字体颜色
        &:hover {
            span {
                color: #3491FA;
            }
        }
        span {
            margin-top: 8px;
        }
    }
}
</style>