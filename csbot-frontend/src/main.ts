import { createApp } from 'vue'
import App from './App.vue'
import '@/styles/reset.scss'
import router from '@/router'
import pinia from '@/pinia'
import ArcoVue from '@arco-design/web-vue'
import '@arco-design/web-vue/dist/arco.css'
import ArcoVueIcon from '@arco-design/web-vue/es/icon';
import '@/permission.ts'

const app = createApp(App)
app.use(router)
app.use(pinia)
app.use(ArcoVue, {
    // 用于改变使用组件时的前缀名称
    componentPrefix: 'a'
});
app.use(ArcoVueIcon);
app.mount('#app')

