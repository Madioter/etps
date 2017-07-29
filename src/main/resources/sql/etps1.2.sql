CREATE TABLE c_admtree
(
    id INT(11) PRIMARY KEY NOT NULL COMMENT '辖区id',
    name VARCHAR(100) COMMENT '辖区名称',
    parent_id INT(11) COMMENT '父级别id',
    state INT(11) COMMENT '是否有子集',
    is_init INT(11) DEFAULT '0' COMMENT '是否被初始化'
);

INSERT INTO c_admtree (id, name, parent_id, state, init) VALUES (370000, '顶级id', null, 0, 0);

CREATE TABLE c_enttree
(
    id INT(11) PRIMARY KEY NOT NULL,
    parent_id INT(11) DEFAULT 0,
    name VARCHAR(100) COMMENT '父级别id',
    state INT(11) COMMENT '是否有子集',
    init INT(11) DEFAULT '0' COMMENT '是否被初始化'
);