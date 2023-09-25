import {defineStore} from "pinia";
import type {UserState} from "@/pinia/modules/user/type.ts";
import {AuthInfo, CommonResponse, GetMeResponse, LoginParams, LoginResponse, UserInfo} from "@/api/user/type.ts";
import {reqGetMe, reqLogin} from "@/api/user";

const useUserStore = defineStore('User', {
    state: (): UserState => {
        return {
            authInfo: {
                accessToken: localStorage.getItem('accessToken') as string || '',
                refreshToken: localStorage.getItem('refreshToken') as string || '',
            } as AuthInfo,
            userInfo: JSON.parse(localStorage.getItem('userInfo') || '{}') as UserInfo
        }
    },
    getters: {

    },
    actions: {
        async afterLogin(authInfo: AuthInfo): Promise<void> {
            // 1.将token保存到localStorage中
            localStorage.setItem('accessToken', authInfo.accessToken)
            localStorage.setItem('refreshToken', authInfo.refreshToken)
            this.authInfo = authInfo

            // 2.向后端获取请求得到个人信息
            const resp: GetMeResponse = await reqGetMe()
            if (resp.statusCode == 0) {
                localStorage.setItem('userInfo', JSON.stringify(resp.data))
                this.userInfo = resp.data
            }
        },
        async login(loginForm: LoginParams): Promise<CommonResponse> {
            const resp: LoginResponse = await reqLogin(loginForm)
            if (resp.statusCode == 0) {
                await this.afterLogin(resp.data)
            }
            return resp as CommonResponse
        },
        clearData(): void {
            localStorage.clear()
            this.authInfo = {
                accessToken: '',
                refreshToken: ''
            } as AuthInfo
            this.userInfo = {} as UserInfo
        }
    }
})

export default useUserStore