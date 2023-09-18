import request from "@/utils/request.ts";
import {CaptchaResponse, LoginParams, LoginResponse} from "@/api/user/type.ts";


enum UserAPI {
    // 获取验证码
    captchaUrl = '/users/public/captcha',
    // 登录
    loginUrl = '/users/public/login',
}

// 获取验证码
export const reqCaptcha = () => request.get<any, CaptchaResponse>(UserAPI.captchaUrl)

// 登录
export const reqLogin = (loginParams: LoginParams) => request.post<any, LoginResponse>(UserAPI.loginUrl, loginParams)

