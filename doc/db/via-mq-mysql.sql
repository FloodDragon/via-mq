
CREATE TABLE `XXL_MQ_MESSAGE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '消息主题',
  `data` text NOT NULL COMMENT '消息数据, Map<String, String>对象系列化的JSON字符串',
  `delay_time` datetime NOT NULL COMMENT '延迟执行的时间, new Date()立即执行, 否则在延迟时间点之后开始执行;',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` varchar(255) NOT NULL COMMENT '消息状态: NEW=新消息、ING=消费中、SUCCESS=消费成功、FAIL=消费失败、TIMEOUT=超时',
  `msg` text COMMENT '历史流转日志',
  `retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '重试次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

