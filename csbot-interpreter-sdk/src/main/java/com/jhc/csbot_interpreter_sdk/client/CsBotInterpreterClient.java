package com.jhc.csbot_interpreter_sdk.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @Description: 脚本解释器客户端
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CsBotInterpreterClient {
    private String scriptUrl;

    public void testReadFile() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(scriptUrl));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                System.out.println("关闭文件流失败");
            }
        }
    }
}
