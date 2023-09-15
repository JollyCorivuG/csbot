import request from "@/utils/request.ts";
import type {RefreshTokenResponse} from "@/api/auth/type.ts";

enum AuthAPI {
    // 刷新 token
    refreshTokenUrl = '/auth/public/refresh',
}

// 刷新 token
export const reqRefreshToken = (rToken: string) => request.get<any, RefreshTokenResponse>(AuthAPI.refreshTokenUrl + `/${rToken}`)