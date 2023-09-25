CREATE DATABASE csbot DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use csbot;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
     `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
     `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
     `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
     `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户头像',
     `status` int(11) NOT NULL DEFAULT 0 COMMENT '使用状态 0.正常 1拉黑',
     `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
     `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE INDEX `uniq_phone`(`phone`) USING BTREE,
     UNIQUE INDEX `uniq_nick_name`(`nick_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for msg
-- ----------------------------
DROP TABLE IF EXISTS `msg`;
CREATE TABLE `msg`  (
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '消息id',
    `room_id` bigint(20) NOT NULL COMMENT '会话id, 即该条消息属于哪个会话',
    `from_uid` bigint(20) NOT NULL COMMENT '消息发送者uid',
    `msg_type` int(11) NOT NULL COMMENT '消息类型',
    `msg_body` json NOT NULL COMMENT '消息主体',
    `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_room_id`(`room_id`) USING BTREE,
    INDEX `idx_from_uid`(`from_uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;