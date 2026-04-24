-- 业务平台扩展：学生/教师/管理员、上传审核、大屏统计

-- 1) 教师科研材料表
drop table if exists biz_teacher_material;
create table biz_teacher_material (
  material_id        bigint(20)      not null auto_increment comment '主键ID',
  teacher_user_id    bigint(20)      not null                comment '教师用户ID',
  teacher_name       varchar(64)     default ''              comment '教师姓名',
  department_name    varchar(128)    default ''              comment '院系/部门',
  title              varchar(255)    not null                comment '材料标题',
  material_type      varchar(64)     default ''              comment '材料类型',
  project_amount     decimal(14,2)   default 0               comment '项目资金',
  description        varchar(1000)   default null            comment '内容说明',
  submit_date        datetime        default current_timestamp comment '提交日期',
  attachment         varchar(2000)   default null            comment '附件(逗号分隔)',
  audit_status       char(1)         default '0'             comment '审核状态(0待审 1通过 2驳回)',
  audit_by_user_id   bigint(20)      default null            comment '审核人ID',
  audit_by_name      varchar(64)     default null            comment '审核人姓名',
  audit_time         datetime        default null            comment '审核时间',
  audit_remark       varchar(500)    default null            comment '审核意见',
  create_by          varchar(64)     default ''              comment '创建者',
  create_time        datetime        default current_timestamp comment '创建时间',
  update_by          varchar(64)     default ''              comment '更新者',
  update_time        datetime        default null on update current_timestamp comment '更新时间',
  remark             varchar(500)    default null            comment '备注',
  primary key (material_id)
) engine=innodb comment='教师科研材料';

-- 2) 学生成果表
drop table if exists biz_student_achievement;
create table biz_student_achievement (
  achievement_id      bigint(20)      not null auto_increment comment '主键ID',
  student_user_id     bigint(20)      not null                comment '学生用户ID',
  student_name        varchar(64)     default ''              comment '学生姓名',
  student_no          varchar(64)     default ''              comment '学号',
  department_name     varchar(128)    default ''              comment '院系/部门',
  achievement_title   varchar(255)    not null                comment '成果标题',
  achievement_type    varchar(64)     default ''              comment '成果类型',
  achievement_level   varchar(64)     default ''              comment '成果等级',
  award_date          datetime        default current_timestamp comment '获奖/完成日期',
  description         varchar(1000)   default null            comment '成果说明',
  attachment          varchar(2000)   default null            comment '附件(逗号分隔)',
  audit_status        char(1)         default '0'             comment '审核状态(0待审 1通过 2驳回)',
  audit_by_user_id    bigint(20)      default null            comment '审核人ID',
  audit_by_name       varchar(64)     default null            comment '审核人姓名',
  audit_time          datetime        default null            comment '审核时间',
  audit_remark        varchar(500)    default null            comment '审核意见',
  create_by           varchar(64)     default ''              comment '创建者',
  create_time         datetime        default current_timestamp comment '创建时间',
  update_by           varchar(64)     default ''              comment '更新者',
  update_time         datetime        default null on update current_timestamp comment '更新时间',
  remark              varchar(500)    default null            comment '备注',
  primary key (achievement_id)
) engine=innodb comment='学生成果信息';

-- 3) 新增角色：学生 / 教师（如已存在则跳过）
insert into sys_role(role_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
select 101, '学生', 'student', 10, '1', 1, 1, '0', '0', 'admin', sysdate(), '学生角色'
where not exists (select 1 from sys_role where role_key = 'student');

insert into sys_role(role_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
select 102, '教师', 'teacher', 11, '1', 1, 1, '0', '0', 'admin', sysdate(), '教师角色'
where not exists (select 1 from sys_role where role_key = 'teacher');

-- 4) 菜单：业务平台目录 + 3个页面（大屏/教师上传/学生上传）
insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2000, '业务平台', 0, 5, 'biz', null, 1, 0, 'M', '0', '0', '', 'chart', 'admin', sysdate(), '业务平台目录'
where not exists (select 1 from sys_menu where menu_id = 2000);

insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2001, '数据大屏', 2000, 1, 'dashboard', 'biz/dashboard/index', 1, 0, 'C', '0', '0', 'biz:dashboard:view', 'dashboard', 'admin', sysdate(), '数据大屏'
where not exists (select 1 from sys_menu where menu_id = 2001);

insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2002, '教师材料上传', 2000, 2, 'teacher', 'biz/teacher/index', 1, 0, 'C', '0', '0', 'biz:teacherMaterial:list', 'upload', 'admin', sysdate(), '教师科研材料'
where not exists (select 1 from sys_menu where menu_id = 2002);

insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2003, '学生成果上传', 2000, 3, 'student', 'biz/student/index', 1, 0, 'C', '0', '0', 'biz:studentAchievement:list', 'form', 'admin', sysdate(), '学生成果上传'
where not exists (select 1 from sys_menu where menu_id = 2003);

-- 5) 按钮权限
insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2101, '教师材料新增', 2002, 1, '#', '', 1, 0, 'F', '0', '0', 'biz:teacherMaterial:add', '#', 'admin', sysdate(), ''
where not exists (select 1 from sys_menu where menu_id = 2101);
insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2102, '教师材料编辑', 2002, 2, '#', '', 1, 0, 'F', '0', '0', 'biz:teacherMaterial:edit', '#', 'admin', sysdate(), ''
where not exists (select 1 from sys_menu where menu_id = 2102);
insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2103, '教师材料删除', 2002, 3, '#', '', 1, 0, 'F', '0', '0', 'biz:teacherMaterial:remove', '#', 'admin', sysdate(), ''
where not exists (select 1 from sys_menu where menu_id = 2103);
insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2104, '教师材料审核', 2002, 4, '#', '', 1, 0, 'F', '0', '0', 'biz:teacherMaterial:audit', '#', 'admin', sysdate(), ''
where not exists (select 1 from sys_menu where menu_id = 2104);

insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2201, '学生成果新增', 2003, 1, '#', '', 1, 0, 'F', '0', '0', 'biz:studentAchievement:add', '#', 'admin', sysdate(), ''
where not exists (select 1 from sys_menu where menu_id = 2201);
insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2202, '学生成果编辑', 2003, 2, '#', '', 1, 0, 'F', '0', '0', 'biz:studentAchievement:edit', '#', 'admin', sysdate(), ''
where not exists (select 1 from sys_menu where menu_id = 2202);
insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2203, '学生成果删除', 2003, 3, '#', '', 1, 0, 'F', '0', '0', 'biz:studentAchievement:remove', '#', 'admin', sysdate(), ''
where not exists (select 1 from sys_menu where menu_id = 2203);
insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2204, '学生成果审核', 2003, 4, '#', '', 1, 0, 'F', '0', '0', 'biz:studentAchievement:audit', '#', 'admin', sysdate(), ''
where not exists (select 1 from sys_menu where menu_id = 2204);

insert into sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
select 2301, '大屏查看', 2001, 1, '#', '', 1, 0, 'F', '0', '0', 'biz:dashboard:view', '#', 'admin', sysdate(), ''
where not exists (select 1 from sys_menu where menu_id = 2301);

-- 6) 角色授权（管理员全开，教师只给教师模块+大屏，学生只给学生模块+大屏）
insert ignore into sys_role_menu(role_id, menu_id) values
(1,2000),(1,2001),(1,2002),(1,2003),(1,2101),(1,2102),(1,2103),(1,2104),(1,2201),(1,2202),(1,2203),(1,2204),(1,2301);

insert ignore into sys_role_menu(role_id, menu_id) values
(102,2000),(102,2001),(102,2002),(102,2101),(102,2102),(102,2301);

insert ignore into sys_role_menu(role_id, menu_id) values
(101,2000),(101,2001),(101,2003),(101,2201),(101,2202),(101,2301);

-- 7) 隐藏当前需求无关的菜单（可按你实际需要再调整）
update sys_menu set visible = '1' where menu_id in (2,3,4,107,108,109,110,111,112,113,114,115,116,117);
