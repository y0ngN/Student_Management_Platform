-- Material standard data model only (no menu insertion)

drop table if exists biz_material_record;
drop table if exists biz_material_field;
drop table if exists biz_material_category;

create table biz_material_category (
  category_id      bigint(20)      not null auto_increment comment 'PK',
  category_code    varchar(64)     not null                comment 'category code',
  category_name    varchar(128)    not null                comment 'category name',
  category_group   varchar(64)     default ''              comment 'group',
  sort_num         int             default 1               comment 'sort',
  status           char(1)         default '0'             comment '0 active 1 disabled',
  description      varchar(500)    default null            comment 'description',
  create_by        varchar(64)     default ''              comment 'created by',
  create_time      datetime        default current_timestamp comment 'created time',
  update_by        varchar(64)     default ''              comment 'updated by',
  update_time      datetime        default null on update current_timestamp comment 'updated time',
  primary key (category_id),
  unique key uk_category_code (category_code)
) engine=innodb comment='material category';

create table biz_material_field (
  field_id         bigint(20)      not null auto_increment comment 'PK',
  category_code    varchar(64)     not null                comment 'category code',
  field_code       varchar(64)     not null                comment 'field code',
  field_name       varchar(128)    not null                comment 'field name',
  field_type       varchar(32)     default 'string'        comment 'string/number/date/select/bool/user/multi_user',
  required_flag    char(1)         default '0'             comment '0 no 1 yes',
  dict_options     varchar(1000)   default null            comment 'comma options',
  sort_num         int             default 1               comment 'sort',
  stat_tag         varchar(64)     default null            comment 'stat tag',
  status           char(1)         default '0'             comment '0 active 1 disabled',
  create_by        varchar(64)     default ''              comment 'created by',
  create_time      datetime        default current_timestamp comment 'created time',
  update_by        varchar(64)     default ''              comment 'updated by',
  update_time      datetime        default null on update current_timestamp comment 'updated time',
  primary key (field_id),
  key idx_category_code (category_code)
) engine=innodb comment='material field definition';

create table biz_material_record (
  record_id          bigint(20)      not null auto_increment comment 'PK',
  category_code      varchar(64)     not null                comment 'category code',
  submitter_user_id  bigint(20)      not null                comment 'submitter id',
  submitter_name     varchar(64)     default ''              comment 'submitter name',
  submitter_role     varchar(32)     default ''              comment 'student/teacher/admin',
  dept_name          varchar(128)    default ''              comment 'department',
  material_title     varchar(255)    not null                comment 'title',
  payload_json       longtext                                  comment 'json data',
  attachment         varchar(2000)   default null            comment 'attachments',
  submit_time        datetime        default current_timestamp comment 'submit time',
  audit_status       char(1)         default '0'             comment '0 pending 1 pass 2 reject',
  audit_by_user_id   bigint(20)      default null            comment 'auditor id',
  audit_by_name      varchar(64)     default null            comment 'auditor name',
  audit_time         datetime        default null            comment 'audit time',
  audit_remark       varchar(500)    default null            comment 'audit remark',
  create_by          varchar(64)     default ''              comment 'created by',
  create_time        datetime        default current_timestamp comment 'created time',
  update_by          varchar(64)     default ''              comment 'updated by',
  update_time        datetime        default null on update current_timestamp comment 'updated time',
  remark             varchar(500)    default null            comment 'remark',
  primary key (record_id),
  key idx_category_code (category_code),
  key idx_submitter_user_id (submitter_user_id),
  key idx_submit_time (submit_time),
  key idx_audit_status (audit_status)
) engine=innodb comment='material record';

insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
values
('Edu_CompetitionMaterial', '竞赛材料', '教研', 1, '0', '竞赛获奖材料', 'admin'),
('Res_ResearchAwardMaterial', '科研奖励材料', '科研', 2, '0', '科研/教学奖励', 'admin'),
('Res_ProjectMaterial', '科研项目材料', '科研', 3, '0', '项目全过程材料', 'admin'),
('Base_StaffMaterial', '教职工信息材料', '人事', 4, '0', '教职工基础信息', 'admin'),
('Edu_EducationMaterial', '学习经历材料', '人事', 5, '0', '学历学位经历', 'admin'),
('Staff_HonorMaterial', '个人荣誉称号材料', '人事', 6, '0', '荣誉称号信息', 'admin'),
('Staff_StartupMaterial', '个人创业情况材料', '人事', 7, '0', '创业信息', 'admin'),
('Staff_PartTimeMaterial', '个人兼职情况材料', '人事', 8, '0', '兼职信息', 'admin'),
('Staff_NewEntryMaterial', '新入职教师记录材料', '人事', 9, '0', '新入职教师信息', 'admin'),
('Staff_SpecialAppointmentMaterial', '特聘教师记录材料', '人事', 10, '0', '特聘教师合同信息', 'admin'),
('Staff_AssessmentMaterial', '历年事业单位考核结果材料', '人事', 11, '0', '年度考核', 'admin'),
('Staff_FamilyMaterial', '妇幼信息材料', '人事', 12, '0', '家庭结构信息', 'admin');
