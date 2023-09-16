package com.jhc.csbot.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Description: kaptcha 配置类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/16
 */
@Configuration
public class KaptchaConfig {
    
 
    @Bean
    public Producer KaptchaProducer() {
    
        Properties kaptchaProperties = new Properties();
        kaptchaProperties.put("kaptcha.border", "no");
        kaptchaProperties.put("kaptcha.textproducer.char.length","4");
        kaptchaProperties.put("kaptcha.image.height","32");
        kaptchaProperties.put("kaptcha.image.width","90");
        kaptchaProperties.put("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
        kaptchaProperties.put("kaptcha.textproducer.font.color","black");
        kaptchaProperties.put("kaptcha.textproducer.font.size","24");
        kaptchaProperties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        kaptchaProperties.put("kaptcha.textproducer.char.string","acdefhkmnprtwxy2345678");
 
        Config config = new Config(kaptchaProperties);
        return config.getProducerImpl();
    }
}