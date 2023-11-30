package com.jhc.csbot.script_interpreter.test.stub;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @Description: 测试桩
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/24
 */
@SpringBootTest
public class StubsTest {
    @Resource
    private HttpReqMock httpReqMock;


    @Test
    void exec() {
        // 1.先建立 ws 连接
        WebSocketStub.connect();

        // 2.发送消息 (读取模拟输入的文件)
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(StubConstants.SIMULATED_INPUT_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                // 将消息写入模拟输出的文件
                String content = "用户说的话：" + line + "\n";
                Files.writeString(Path.of(StubConstants.SIMULATED_OUTPUT_PATH),
                        content,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);

                // 发送消息
                httpReqMock.sendMsg(line);
                // 休眠 1s (尽可能真实地模拟用户的输入)
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭文件流
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
