package com.jhc.csbot.script_interpreter.test.stub;

import com.jhc.csbot.script_interpreter.core.interpreter.environment.UsersState;
import com.jhc.csbot.script_interpreter.test.auto.modules.TestActuator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @Description: WebSocket 测试桩
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/24
 */
public class WebSocketStub {
    /**
     * 发送消息
     * @param msg
     */
    public static void sendMsg(String msg) {
        String content = "机器人说的话：" + msg + "\n";
        // 测试桩
//        try {
//            Files.writeString(Path.of(StubConstants.SIMULATED_OUTPUT_PATH),
//                    content,
//                    StandardOpenOption.CREATE,
//                    StandardOpenOption.APPEND);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        // 自动测试环境
        try {
            Files.writeString(Path.of(TestActuator.curTestGenerateFile),
                    content,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 建立连接
     */
    public static void connect() {
        UsersState.init(StubConstants.DEFAULT_USER_ID);
    }
}
