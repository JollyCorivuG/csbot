import {AuthInfo, UserInfo} from "@/api/user/type.ts";

export interface UserState {
    authInfo: AuthInfo,
    userInfo: UserInfo
}