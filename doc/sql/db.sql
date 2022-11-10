create table t_user
(
    id bigint auto_increment comment '主键' primary key,
    username varchar(100) null comment '用户名',
    password varchar(255) null comment '密码',
    gender tinyint null comment '性别 0未知 1男 2女',
    age tinyint not null comment '年龄',
    phone varchar(20) null comment '手机',
    address varchar(255) null comment '地址',
    status tinyint null comment '数据状态 0正常1删除',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null comment '更新时间'
);

-- auto-generated definition
create table t_auth_client
(
    id          bigint auto_increment comment '主键'
        primary key,
    client      varchar(20)                        null comment '客户端code',
    secret      varchar(100)                       null comment '客户端秘钥',
    expiration  bigint                             null comment '过期时长',
    status      tinyint  default 0                 null,
    create_time datetime default CURRENT_TIMESTAMP null,
    update_time datetime default CURRENT_TIMESTAMP null
)
    comment '认证客户端';

-- auto-generated definition
create table t_api_request_log
(
    id               bigint auto_increment comment '主键'
        primary key,
    func             varchar(20)                        null comment '功能',
    request_method   varchar(100)                       null comment '方法名',
    request_url      varchar(200)                       null comment '请求地址',
    request_params   json                               null comment '请求参数',
    request_time     datetime default CURRENT_TIMESTAMP null comment '请求时间',
    request_duration bigint                             null comment '请求时长',
    execute_result   tinyint  default 0                 null comment '执行结果：0成功 1失败',
    execute_message  json                               null comment '执行返回信息',
    create_time      datetime default CURRENT_TIMESTAMP null,
    update_time      datetime default CURRENT_TIMESTAMP null
)
    comment '接口请求日志';



