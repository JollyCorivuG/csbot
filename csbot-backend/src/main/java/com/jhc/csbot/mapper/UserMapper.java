package com.jhc.csbot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jhc.csbot.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 用户 Mapper
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
