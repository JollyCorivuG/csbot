import {defineStore} from "pinia";
import type {UserState} from "@/pinia/modules/user/type.ts";
import {AuthInfo, CommonResponse, LoginParams, LoginResponse} from "@/api/user/type.ts";
import {reqLogin} from "@/api/user";

const useUserStore = defineStore('User', {
    state: (): UserState => {
        return {
            authInfo: {
                aToken: localStorage.getItem('aToken') as string || '',
                rToken: localStorage.getItem('rToken') as string || '',
            } as AuthInfo
        }
    },
    getters: {

    },
    actions: {
        async afterLogin(authInfo: AuthInfo): Promise<void> {
            localStorage.setItem('aToken', authInfo.aToken)
            localStorage.setItem('rToken', authInfo.rToken)
            this.authInfo = authInfo
        },
        async login(loginForm: LoginParams): Promise<CommonResponse> {
            const resp: LoginResponse = await reqLogin(loginForm)
            if (resp.statusCode == 0) {
                await this.afterLogin(resp.data)
            }
            return resp as CommonResponse
        }
    }
})

export default useUserStore