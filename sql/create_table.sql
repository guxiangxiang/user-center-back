-- auto-generated definition
create database `usercenter`;

create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                       not null comment '账号',
    userPassword varchar(512)                       not null comment '密码',
    username     varchar(256)                       null comment '用户昵称',
    gender       tinyint                            null comment '性别 0-男 1-女',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    phone        varchar(128)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '状态 0-正常',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色 0-普通用户 1-管理员',
    planetCode   varchar(512)                       null comment '星球编号'
)
    comment '用户' collate = utf8mb4_unicode_ci;

create index idx_userAccount
    on user (userAccount);

