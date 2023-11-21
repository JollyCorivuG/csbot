import request from "@/utils/request.ts";
import {
    CaptchaResponse,
    GetMeResponse,
    GetOnlineUserListResponse,
    LoginParams,
    LoginResponse
} from "@/api/user/type.ts";


enum UserAPI {
    // 获取验证码
    captchaUrl = '/users/public/captcha',
    // 登录
    loginUrl = '/users/public/login',
    // 获取当前登录用户信息
    getMeUrl = '/users/me',
    // 获取在线用户列表
    getOnlineUserList = 'users/online'
}

// 获取验证码
export const reqCaptcha = () => request.get<any, CaptchaResponse>(UserAPI.captchaUrl)

// 登录
export const reqLogin = (loginParams: LoginParams) => request.post<any, LoginResponse>(UserAPI.loginUrl, loginParams)

// 获取当前登录用户信息
export const reqGetMe = () => request.get<any, GetMeResponse>(UserAPI.getMeUrl)

// 获取在线用户列表
export const reqOnlineUserList = () => request.get<any, GetOnlineUserListResponse>(UserAPI.getOnlineUserList)

