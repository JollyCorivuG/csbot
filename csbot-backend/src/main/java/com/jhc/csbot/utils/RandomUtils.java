package com.jhc.csbot.utils;

import cn.hutool.core.util.RandomUtil;
import com.jhc.csbot.common.constants.SysConstants;
import com.jhc.csbot.model.dto.user.RandomUserInfo;

/**
 * @Description: 随机工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
public class RandomUtils {
    private static final String randomAvatarApi = "https://api.multiavatar.com";

    public static RandomUserInfo getRandomUser() {
        String randomNickName = SysConstants.DEFAULT_NICK_NAME_PREFIX + RandomUtil.randomString(6);
        return RandomUserInfo.builder()
                .nickName(randomNickName)
                .avatar(randomAvatarApi + "/" + randomNickName + ".png")
                .build();
    }
}
