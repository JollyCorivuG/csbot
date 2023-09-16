// 通用响应信息
export interface CommonResponse {
    statusCode: number,
    statusMsg: string
}

// Auth 信息
export interface AuthInfo {
    aToken: string,
    rToken: string
}

// captcha 信息
export interface CaptchaInfo {
    base64: string,
    width: number,
    height: number
}

// 登录返回响应
export interface LoginResponse extends CommonResponse {
    data: AuthInfo
}

// 获取验证码返回响应
export interface CaptchaResponse extends CommonResponse {
    data: CaptchaInfo
}


