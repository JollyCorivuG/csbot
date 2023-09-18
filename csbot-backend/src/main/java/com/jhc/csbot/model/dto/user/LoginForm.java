package com.jhc.csbot.model.dto.user;

import lombok.Data;

/**
 * @Description: 登录表单
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
@Data
public class LoginForm {
    private String phone;
    private Long captchaId;
    private String captcha;
}
