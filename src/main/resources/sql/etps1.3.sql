ALTER TABLE etps.t_attachment ADD COLUMN state tinyint DEFAULT 1 COMMENT '状态';
ALTER TABLE etps.t_attachment ADD COLUMN error VARCHAR(2000) DEFAULT '' COMMENT '异常信息';
ALTER TABLE etps.t_attachment ADD COLUMN param VARCHAR(2000) DEFAULT '' COMMENT '参数';