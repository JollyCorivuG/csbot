package com.jhc.csbot.common.exception;

import com.jhc.csbot.common.domain.enums.ErrorStatus;
import lombok.Getter;

/**
 * @Description: 业务异常
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/11
 */
@Getter
public class BizException extends RuntimeException {
    private final Integer code;

    public BizException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.code = errorStatus.getCode();
    }

    public BizException(ErrorStatus errorStatus, String message) {
        super(message);
        this.code = errorStatus.getCode();
    }
}
