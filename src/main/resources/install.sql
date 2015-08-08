/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     8/4/2015 9:13:01 AM                          */
/*==============================================================*/


drop table if exists consulter;

drop table if exists consulter_order;

drop table if exists contact;

drop table if exists customer;

drop table if exists evaluation;

drop table if exists integral_record;

drop table if exists message;

drop table if exists news;

/*==============================================================*/
/* Table: consulter                                             */
/*==============================================================*/
create table consulter
(
   id                   int not null auto_increment comment '主键',
   username             varchar(256) not null comment '登录用户名',
   password             varchar(256) not null comment '密码',
   openid				varchar(256) not null comment 'openid',
   nickname             varchar(256) not null comment '昵称',
   headurl              varchar(256) comment '头像',
   location             int not null comment '地区',
   childsex             int comment '宝宝性别  0未知 1男孩 2女孩',
   childname            varchar(256) comment '宝宝姓名',
   childbirth           bigint(20) comment '宝宝生日',
   phone                varchar(20) comment '电话',
   primary key (id)
)
DEFAULT CHARSET=utf8;

alter table consulter comment '咨询者、病人、粉丝';

/*==============================================================*/
/* Table: consulter_order                                       */
/*==============================================================*/
create table consulter_order
(
   id                   int not null auto_increment,
   consulter_id         int not null comment '发起人id',
   customer_id          int comment '当前客服id',
   evaluation_id        int,
   status               int not null comment '0 未接 1 以接 2 转接中',
   server_status        int not null comment '0 未结束 1 结束',
   create_time          bigint(20) not null,
   primary key (id)
)
DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: contact                                               */
/*==============================================================*/
create table contact
(
   id                   int not null auto_increment,
   customer_id          int not null,
   consulter_id         int not null,
   contact_time         bigint(20) not null,
   primary key (id)
)
DEFAULT CHARSET=utf8;

alter table contact comment '最近一次联系的时间记录';

/*==============================================================*/
/* Table: customer                                              */
/*==============================================================*/
create table customer
(
   id                   int not null auto_increment,
   username             varchar(256) not null,
   password             varchar(256) not null,
   nickname             varchar(256) not null,
   headurl              varchar(256),
   location             int not null,
   Integral             bigint not null,
   company              varchar(256),
   department           varchar(256),
   memo                 text,
   serviceTimes         int not null,
   online               int not null comment '是否在线',
   primary key (id)
)
DEFAULT CHARSET=utf8;

alter table customer comment '医生，客服表';

/*==============================================================*/
/* Table: evaluation                                            */
/*==============================================================*/
create table evaluation
(
   id                   int not null auto_increment,
   order_id             int not null,
   type                 int not null,
   service_times        int,
   service_attitude     int,
   service_level        int,
   content              text,
   primary key (id)
)
DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: integral_record                                       */
/*==============================================================*/
create table integral_record
(
   id                   int not null auto_increment,
   customer_id          int not null,
   create_time          bigint(20) not null,
   addpoint             int not null comment '添加积分数',
   memo                 varchar(256) comment '备注',
   primary key (id)
)
DEFAULT CHARSET=utf8;

alter table integral_record comment '积分记录';

/*==============================================================*/
/* Table: message                                               */
/*==============================================================*/
create table message
(
   id                   int not null auto_increment,
   order_id             int not null,
   sender_id            int not null,
   sender_type          int not null comment '0 病人发送 1医生发送',
   create_time          bigint(20) not null,
   content              text,
   primary key (id)
)
DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: news                                                  */
/*==============================================================*/
create table news
(
   id                   int not null auto_increment,
   create_time          bigint(20) not null,
   title                varchar(256) not null,
   content              text,
   primary key (id)
)
DEFAULT CHARSET=utf8;

alter table news comment '育儿教程';

alter table consulter_order add constraint FK_consulter_order foreign key (consulter_id)
      references consulter (id) on delete restrict on update restrict;

alter table consulter_order add constraint FK_customer_order foreign key (customer_id)
      references customer (id) on delete restrict on update restrict;

alter table consulter_order add constraint FK_evaluation_order foreign key (evaluation_id)
      references evaluation (id) on delete restrict on update restrict;

alter table contact add constraint FK_consulter_contact foreign key (consulter_id)
      references consulter (id) on delete restrict on update restrict;

alter table contact add constraint FK_customer_contact foreign key (customer_id)
      references customer (id) on delete restrict on update restrict;

alter table evaluation add constraint FK_evaluation_order2 foreign key (order_id)
      references consulter_order (id) on delete restrict on update restrict;

alter table integral_record add constraint FK_customer_integral foreign key (customer_id)
      references customer (id) on delete restrict on update restrict;

alter table message add constraint FK_order_message foreign key (order_id)
      references consulter_order (id) on delete restrict on update restrict;

