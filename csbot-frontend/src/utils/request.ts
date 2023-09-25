// 对axios库的二次封装
import axios, {AxiosInstance} from 'axios'
import router from '@/router'
import {RefreshTokenResponse} from "@/api/auth/type.ts";
import {reqRefreshToken} from "@/api/auth";
import useUserStore from "@/pinia/modules/user";
import {Message} from "@arco-design/web-vue";

// 创建一个axios实例: 可以设置基础路径, 超时时间等
const request: AxiosInstance = axios.create({
    // @ts-ignore
    baseURL: import.meta.env.VITE_API_PREFIX as string,
    timeout: 30000
})

// 请求拦截器
request.interceptors.request.use((config) => {
    // config: 请求拦截器回调注入的对象(配置对象), 配置对象有headers属性
    const userStore = useUserStore()
    if (userStore.authInfo) {
        config.headers.Authorization = 'Bearer ' + userStore.authInfo.accessToken;
    }
    return config
})

// 响应拦截器
request.interceptors.response.use((response) => {
    // response: 响应拦截器回调注入的对象(响应对象), 响应对象有data属性
    return response.data
}, async (error) => {
    // 处理http错误状态码
    const {status, config} = error.response

    switch (status) {
        case 404:
            break;
        case 500 | 501 | 502 | 503 | 504 | 505:
            break;
        case 401:
            // 如果返回 401, 说明 a_token 验证失败, 需要用 r_token 刷新
            const resp: RefreshTokenResponse = await reqRefreshToken(localStorage.getItem('r_token') as string)
            if (resp.statusCode == 0) {
                // 刷新成功, 保存新的 a_token 和 r_token
                localStorage.setItem('a_token', resp.data.accessToken)
                localStorage.setItem('r_token', resp.data.refreshToken)
                // 重新发送请求
                return request(config)
            }

            // 如果没成功说明登录过期, 返回登录页
            const userStore = useUserStore()
            userStore.clearData()
            router.push({path: '/login'}).then(r => r)
            Message.error('登录过期, 请重新登录!')
            break;
    }
    return Promise.reject(new Error(error.message))
})

// 导出
export default request