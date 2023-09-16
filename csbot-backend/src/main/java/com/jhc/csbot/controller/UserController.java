package com.jhc.csbot.controller;

import com.google.code.kaptcha.Producer;
import com.jhc.csbot.common.constants.RedisConstants;
import com.jhc.csbot.common.domain.enums.ErrorStatus;
import com.jhc.csbot.common.domain.vo.resp.BasicResponse;
import com.jhc.csbot.common.exception.BizException;
import com.jhc.csbot.model.vo.user.CaptchaImgInfo;
import com.jhc.csbot.utils.RedisUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 用户控制器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/16
 */
@RestController
@RequestMapping("/capi/users")
@Tag(name = "用户相关接口")
public class UserController {
    @Resource
    private Producer captchaProducer;

    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/public/captcha")
    @Operation(summary = "得到验证码")
    public BasicResponse<CaptchaImgInfo> captcha() {
        // 1.生成验证码并保存到 redis
        String captchaText = captchaProducer.createText();
        redisUtils.setWithExpireTime(RedisConstants.CAPTCHA_PREFIX + captchaText, captchaText, RedisConstants.CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
        BufferedImage captchaImage = captchaProducer.createImage(captchaText);

        // 2.将验证码图片转换为base64
        String base64 = null;
        try {
            File tempFile = Files.createTempFile("captcha", ".png").toFile();
            ImageIO.write(captchaImage, "png", tempFile);
            base64 = "data:image/png;base64," + Base64.encodeBase64String(Files.readAllBytes(tempFile.toPath()));
        } catch (Exception e) {
            throw new BizException(ErrorStatus.SYSTEM_ERROR, "生成验证码失败");
        }

        // 3.返回验证码图片信息
        return BasicResponse.success(CaptchaImgInfo.builder()
                .base64(base64)
                .width(captchaImage.getWidth())
                .height(captchaImage.getHeight())
                .build());
    }
}
