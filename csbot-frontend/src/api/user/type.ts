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
    id: number
    base64: string,
    width: number,
    height: number
}

// 获取验证码返回响应
export interface CaptchaResponse extends CommonResponse {
    data: CaptchaInfo
}

// 登录请求参数
export interface LoginParams {
    phone: string,
    captchaId: number,
    captcha: string
}

// 登录返回响应
export interface LoginResponse extends CommonResponse {
    data: AuthInfo
}


