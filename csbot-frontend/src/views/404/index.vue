<template>
    <a-result status="404" subtitle="抱歉, 您所访问的页面不存在！！！">
        <template #extra>
            <a-space>
                <a-button type="primary" @click="back">返回</a-button>
            </a-space>
        </template>
    </a-result>
</template>

<script setup lang="ts">
const back = () => {
    history.back()
}
</script>

<style scoped lang="scss">

</style>