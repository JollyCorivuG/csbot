package com.jhc.csbot.interpreter;

import com.jhc.csbot.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Description: 脚本解释器测试类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/30
 */
@SpringBootTest
public class InterpreterTest {

    @Test
    void test() {
        // 根据类型得到类
        try {
            Class<?> clazz = Class.forName("com.jhc.csbot.model.entity.User"); // 使用类名获取Class对象
            // 得到一个实例
            Object instance = null;
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true); // 如果构造函数是私有的，需要设置可访问性
            instance = constructor.newInstance();

            if (instance instanceof User user) {
                user.setAvatar("dsa");
                System.out.println(user);
            }

        } catch (ClassNotFoundException e) {
            // 处理类未找到的异常
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
