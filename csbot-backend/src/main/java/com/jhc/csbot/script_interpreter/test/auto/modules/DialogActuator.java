package com.jhc.csbot.script_interpreter.test.auto.modules;

import com.jhc.csbot.script_interpreter.test.stub.HttpReqMock;
import com.jhc.csbot.script_interpreter.test.stub.WebSocketStub;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @Description: 对话执行器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/26
 */
@Component
public class DialogActuator {

    @Resource
    private HttpReqMock httpReqMock;

    /**
     * 执行对话
     * @param dialogPath
     */
    public void exec(String dialogPath) {
        // 1.先建立 ws 连接
        WebSocketStub.connect();

        // 2.发送消息 (读取模拟输入的文件)
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(dialogPath));
            String line;
            while ((line = reader.readLine()) != null) {
                // 将消息写入模拟输出的文件
                String content = "用户说的话：" + line + "\n";
                Files.writeString(Path.of(TestActuator.curTestGenerateFile),
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
