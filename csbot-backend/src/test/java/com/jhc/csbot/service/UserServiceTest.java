package com.jhc.csbot.service;

import com.jhc.csbot.common.constants.SysConstants;
import com.jhc.csbot.dao.user.UserDao;
import com.jhc.csbot.model.dto.user.RandomUserInfo;
import com.jhc.csbot.model.entity.User;
import com.jhc.csbot.utils.RandomUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: 用户服务测试
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserDao userDao;

    @Test
    void testSaveUser() {
        RandomUserInfo randomUserInfo = RandomUtils.getRandomUser();
        User user = User.builder()
                .phone("18611555833")
                .nickName(randomUserInfo.getNickName())
                .avatar(randomUserInfo.getAvatar())
                .status(SysConstants.USER_STATUS_NORMAL)
                .build();
        userDao.save(user);
    }

    @Test
    void testQueryUser() {
        User user = userDao.getUserByPhone("18611555833");
        System.out.println(user);
    }

}
