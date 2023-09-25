<template>
    <a-textarea auto-size style="background: none;color: white;" placeholder="问点什么吧~" v-model="msg" />
    <svg :class="{active: msg.length != 0}" x="1695286943202" class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" p-id="4140" width="28" height="28"><path d="M871.04 89.770667L120.064 380.16a51.2 51.2 0 0 0-1.792 94.762667l303.36 130.56 131.072 303.957333a51.2 51.2 0 0 0 94.805333-1.877333l289.792-751.573334a51.2 51.2 0 0 0-66.261333-66.133333z m-41.130667 107.392l-231.978666 601.642666-97.962667-227.114666-3.584-7.338667a85.333333 85.333333 0 0 0-41.045333-37.248l-226.56-97.536 601.173333-232.405333z" :fill="msg.length != 0 ? '#ffffff' : '#999999'" p-id="4141"></path></svg>
</template>

<script setup lang="ts">
import {ref} from "vue";

const msg = ref<string>('')
</script>

<style scoped lang="scss">
svg {
    &.active {
        cursor: pointer;
    }
    margin-left: 8px;
    margin-right: 8px;
}
</style>