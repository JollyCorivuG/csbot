package com.jhc.csbot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jhc.csbot.model.entity.Msg;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 消息 Mapper
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@Mapper
public interface MsgMapper extends BaseMapper<Msg> {
}
