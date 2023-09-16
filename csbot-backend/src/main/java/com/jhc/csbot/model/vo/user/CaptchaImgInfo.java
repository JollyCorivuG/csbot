package com.jhc.csbot.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 验证码图片信息类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaImgInfo {
    private String base64;
    private Integer width;
    private Integer height;
}
