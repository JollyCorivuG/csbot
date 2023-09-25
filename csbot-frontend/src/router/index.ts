import { createRouter, createWebHistory } from 'vue-router'

export default createRouter({
    // 路由配置
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/login',
            component: () => import('@/views/login/index.vue')
        },
        {
            path: '/index',
            component: () => import('@/views/index/index.vue')
        },
        {
            path: '/404',
            component: () => import('@/views/404/index.vue')
        }
    ],
    // 滚动行为:控制滚动条的位置
    scrollBehavior() {
        return {
            left: 0,
            top: 0
        }
    }
})