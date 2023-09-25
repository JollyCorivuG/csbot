package com.jhc.csbot.dao.msg;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhc.csbot.mapper.MsgMapper;
import com.jhc.csbot.model.entity.Msg;
import org.springframework.stereotype.Repository;

/**
 * @Description: 消息数据访问对象
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@Repository
public class MsgDao extends ServiceImpl<MsgMapper, Msg> {
    /**
     * 保存消息
     * @param msg
     * @return
     */
    public Msg saveMsg(Msg msg) {
        this.save(msg);
        return msg;
    }

    /**
     * 根据 ID 获取消息
     * @param id
     * @return
     */
    public Msg getMsgById(Long id) {
        return this.getById(id);
    }
}
