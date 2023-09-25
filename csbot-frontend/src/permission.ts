// 添加路由守卫
import router from "@/router";
import {NavigationGuardNext, RouteLocationNormalized} from "vue-router";
import {Message} from "@arco-design/web-vue";
import useUserStore from "@/pinia/modules/user";

// 前置守卫
// @ts-ignore
router.beforeEach(async (to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) => {
    // 如果是404页面, 则直接跳转
    if (to.path === '/404') {
        next()
        return
    }

    // 如果路径不在路由表中, 则跳转到404页面
    if (router.resolve(to.path).matched.length === 0) {
        next('/404')
        return
    }

    // 根据token进行路由鉴权
    const token = localStorage.getItem('accessToken')
    if (to.path !== '/login' && to.path !== '/') {
        if (!token) {
            Message.error('请先登录!')
            next('/login')
        } else {
            next()
        }
        return
    }

    // 如果已经登录, 则进行登出操作
    if (token) {
        if (to.path == '/login') {
            next('/index')
            return
        }

        // 如果是从其他页面跳转到登录页面, 则进行登出操作
        const userStore = useUserStore()
        userStore.clearData()
        Message.info('退出登录!')
    }

    next()
})