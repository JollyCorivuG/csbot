import request from "@/utils/request.ts";
import {CaptchaResponse} from "@/api/user/type.ts";


enum UserAPI {
    // 获取验证码
    captchaUrl = '/users/public/captcha',
}

// 获取验证码
export const reqCaptcha = () => request.get<any, CaptchaResponse>(UserAPI.captchaUrl)

