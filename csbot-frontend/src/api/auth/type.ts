// 通用响应信息
import {AuthInfo} from "@/api/user/type.ts";

export interface CommonResponse {
    statusCode: number,
    statusMsg: string
}

export interface RefreshTokenResponse extends CommonResponse {
    data: AuthInfo
}