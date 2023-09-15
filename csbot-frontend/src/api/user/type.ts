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

// 登录返回响应
export interface LoginResponse extends CommonResponse {
    data: AuthInfo
}