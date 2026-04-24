-- Demo seed data for business platform and dashboard
-- Safe to re-run: this script only cleans rows created by `create_by = 'seed_demo'`.

SET NAMES utf8mb4;
SET SQL_SAFE_UPDATES = 0;

-- 0) Cleanup old demo rows
delete from biz_teacher_material where create_by = 'seed_demo';
delete from biz_student_achievement where create_by = 'seed_demo';
delete from biz_material_record where create_by = 'seed_demo';
delete from biz_material_field where create_by = 'seed_demo';

-- 1) Ensure category master data exists (idempotent)
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Edu_CompetitionMaterial', '竞赛材料', '教研', 1, '0', '竞赛获奖材料', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Edu_CompetitionMaterial');
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Res_ResearchAwardMaterial', '科研奖励材料', '科研', 2, '0', '科研/教学奖励', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Res_ResearchAwardMaterial');
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Res_ProjectMaterial', '科研项目材料', '科研', 3, '0', '项目全过程材料', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Res_ProjectMaterial');
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Base_StaffMaterial', '教职工信息材料', '人事', 4, '0', '教职工基础信息', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Base_StaffMaterial');
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Edu_EducationMaterial', '学习经历材料', '人事', 5, '0', '学历学位经历', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Edu_EducationMaterial');
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Staff_HonorMaterial', '个人荣誉称号材料', '人事', 6, '0', '荣誉称号信息', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Staff_HonorMaterial');
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Staff_StartupMaterial', '个人创业情况材料', '人事', 7, '0', '创业信息', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Staff_StartupMaterial');
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Staff_PartTimeMaterial', '个人兼职情况材料', '人事', 8, '0', '兼职信息', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Staff_PartTimeMaterial');
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Staff_NewEntryMaterial', '新入职教师记录材料', '人事', 9, '0', '新入职教师信息', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Staff_NewEntryMaterial');
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Staff_SpecialAppointmentMaterial', '特聘教师记录材料', '人事', 10, '0', '特聘教师合同信息', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Staff_SpecialAppointmentMaterial');
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Staff_AssessmentMaterial', '历年事业单位考核结果材料', '人事', 11, '0', '年度考核', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Staff_AssessmentMaterial');
insert into biz_material_category(category_code, category_name, category_group, sort_num, status, description, create_by)
select 'Staff_FamilyMaterial', '妇幼信息材料', '人事', 12, '0', '家庭结构信息', 'seed_demo'
where not exists (select 1 from biz_material_category where category_code = 'Staff_FamilyMaterial');

-- 2) Seed teacher materials (for dashboard counts/amount/month trend)
insert into biz_teacher_material
(teacher_user_id, teacher_name, department_name, title, material_type, project_amount, description, submit_date, attachment,
 audit_status, audit_by_user_id, audit_by_name, audit_time, audit_remark, create_by, create_time, update_by, remark)
values
(20001,'张伟','计算机学院','国家自然科学基金青年项目','科研项目',300000.00,'人工智能方向项目','2025-10-08 10:15:00','/profile/upload/demo/t1.pdf',
 '1',1,'管理员','2025-10-10 09:00:00','通过','seed_demo',sysdate(),'seed_demo','演示数据'),
(20002,'李娜','机械工程学院','省重点研发计划课题','科研项目',180000.00,'智能制造课题','2025-11-12 14:30:00','/profile/upload/demo/t2.pdf',
 '1',1,'管理员','2025-11-13 09:10:00','通过','seed_demo',sysdate(),'seed_demo','演示数据'),
(20003,'王强','经济管理学院','横向课题：产业数字化咨询','项目提案',90000.00,'企业数字化改造','2025-12-02 11:20:00','/profile/upload/demo/t3.pdf',
 '2',1,'管理员','2025-12-04 16:40:00','补充预算明细','seed_demo',sysdate(),'seed_demo','演示数据'),
(20001,'张伟','计算机学院','高水平论文：知识图谱推理','论文',0.00,'CCF-A论文提交材料','2026-01-09 08:50:00','/profile/upload/demo/t4.pdf',
 '1',1,'管理员','2026-01-10 13:20:00','通过','seed_demo',sysdate(),'seed_demo','演示数据'),
(20004,'赵敏','数学与统计学院','教学成果奖申报材料','科研奖励',20000.00,'省级教学成果奖','2026-01-24 15:05:00','/profile/upload/demo/t5.pdf',
 '0',null,null,null,null,'seed_demo',sysdate(),'seed_demo','演示数据'),
(20005,'陈杰','外国语学院','人文社科项目结题报告','科研项目',60000.00,'结题验收材料','2026-02-14 09:40:00','/profile/upload/demo/t6.pdf',
 '1',1,'管理员','2026-02-16 10:00:00','通过','seed_demo',sysdate(),'seed_demo','演示数据'),
(20002,'李娜','机械工程学院','发明专利申请支撑材料','科研奖励',15000.00,'发明专利转化','2026-02-28 17:10:00','/profile/upload/demo/t7.pdf',
 '0',null,null,null,null,'seed_demo',sysdate(),'seed_demo','演示数据'),
(20006,'周婷','信息工程学院','省自然科学奖申报书','科研奖励',50000.00,'省奖申报','2026-03-06 10:45:00','/profile/upload/demo/t8.pdf',
 '1',1,'管理员','2026-03-08 09:15:00','通过','seed_demo',sysdate(),'seed_demo','演示数据'),
(20007,'孙浩','体育学院','校级重点课程建设项目','项目提案',30000.00,'课程建设','2026-03-21 13:30:00','/profile/upload/demo/t9.pdf',
 '2',1,'管理员','2026-03-22 14:20:00','材料不完整','seed_demo',sysdate(),'seed_demo','演示数据'),
(20008,'刘洋','法学院','法治教育研究课题申请','科研项目',120000.00,'法学课题','2026-04-03 09:25:00','/profile/upload/demo/t10.pdf',
 '1',1,'管理员','2026-04-05 11:35:00','通过','seed_demo',sysdate(),'seed_demo','演示数据');

-- 3) Seed student achievements
insert into biz_student_achievement
(student_user_id, student_name, student_no, department_name, achievement_title, achievement_type, achievement_level,
 award_date, description, attachment, audit_status, audit_by_user_id, audit_by_name, audit_time, audit_remark,
 create_by, create_time, update_by, remark)
values
(30001,'吴昊','2022101001','计算机学院','中国大学生计算机设计大赛','竞赛','国家级二等奖',
 '2025-10-16 11:00:00','算法赛道获奖','/profile/upload/demo/s1.pdf','1',1,'管理员','2025-10-18 09:30:00','通过',
 'seed_demo',sysdate(),'seed_demo','演示数据'),
(30002,'郑雪','2022102003','机械工程学院','机械创新设计竞赛','竞赛','省级一等奖',
 '2025-11-20 14:10:00','结构优化方向','/profile/upload/demo/s2.pdf','1',1,'管理员','2025-11-21 10:10:00','通过',
 'seed_demo',sysdate(),'seed_demo','演示数据'),
(30003,'何晨','2022103006','经济管理学院','创新创业训练项目结题','项目','校级优秀',
 '2025-12-09 16:20:00','电商运营课题','/profile/upload/demo/s3.pdf','0',null,null,null,null,
 'seed_demo',sysdate(),'seed_demo','演示数据'),
(30004,'马琳','2022101012','计算机学院','软件著作权登记','学术成果','校级',
 '2026-01-12 09:00:00','教育类小程序','/profile/upload/demo/s4.pdf','1',1,'管理员','2026-01-13 15:45:00','通过',
 'seed_demo',sysdate(),'seed_demo','演示数据'),
(30005,'唐宇','2022104011','数学与统计学院','数学建模竞赛','竞赛','国家级三等奖',
 '2026-01-29 10:35:00','建模B题','/profile/upload/demo/s5.pdf','2',1,'管理员','2026-01-31 08:50:00','证明材料不完整',
 'seed_demo',sysdate(),'seed_demo','演示数据'),
(30006,'许菲','2022105002','外国语学院','英语演讲比赛','竞赛','省级二等奖',
 '2026-02-17 13:05:00','英语公共演讲','/profile/upload/demo/s6.pdf','1',1,'管理员','2026-02-19 10:30:00','通过',
 'seed_demo',sysdate(),'seed_demo','演示数据'),
(30007,'韩涛','2022106015','信息工程学院','电子设计竞赛','竞赛','省级一等奖',
 '2026-03-09 09:40:00','嵌入式系统','/profile/upload/demo/s7.pdf','0',null,null,null,null,
 'seed_demo',sysdate(),'seed_demo','演示数据'),
(30008,'范婷','2022107009','法学院','法学案例分析大赛','竞赛','校级一等奖',
 '2026-04-07 17:00:00','案例辩论方向','/profile/upload/demo/s8.pdf','1',1,'管理员','2026-04-08 10:12:00','通过',
 'seed_demo',sysdate(),'seed_demo','演示数据');

-- 4) Seed standardized records for 12 categories
insert into biz_material_record
(category_code, submitter_user_id, submitter_name, submitter_role, dept_name, material_title, payload_json, attachment,
 submit_time, audit_status, audit_by_user_id, audit_by_name, audit_time, audit_remark, create_by, create_time, update_by, remark)
values
('Edu_CompetitionMaterial',30001,'吴昊','student','计算机学院','竞赛材料-计算机设计大赛',
 '{"竞赛名称":"中国大学生计算机设计大赛","竞赛级别":"国赛","奖项等级":"二等奖","获奖时间":"2025-10-16","指导老师":["张伟"]}',
 '/profile/upload/demo/m1.pdf','2025-10-16 11:00:00','1',1,'管理员','2025-10-18 09:30:00','通过','seed_demo',sysdate(),'seed_demo','标准化演示'),
('Res_ResearchAwardMaterial',20006,'周婷','teacher','信息工程学院','科研奖励-省自然科学奖申报',
 '{"奖励名称":"省自然科学奖","奖励类型":"科技奖","奖励等级":"二等奖","完成人":["周婷","李娜"],"获得时间":"2026-03-06"}',
 '/profile/upload/demo/m2.pdf','2026-03-06 10:45:00','1',1,'管理员','2026-03-08 09:15:00','通过','seed_demo',sysdate(),'seed_demo','标准化演示'),
('Res_ProjectMaterial',20001,'张伟','teacher','计算机学院','科研项目-青年基金',
 '{"项目编号":"NSFC-2025-Y123","项目类别":"国家级","项目负责人":"张伟","项目状态":"在研","合同金额":300000}',
 '/profile/upload/demo/m3.pdf','2025-10-08 10:15:00','1',1,'管理员','2025-10-10 09:00:00','通过','seed_demo',sysdate(),'seed_demo','标准化演示'),
('Base_StaffMaterial',20002,'李娜','teacher','机械工程学院','教职工信息-李娜',
 '{"教职工号":"T2020002","姓名":"李娜","职称":"副教授","岗位类型":"科研","最高学历":"博士"}',
 '/profile/upload/demo/m4.pdf','2026-02-28 17:10:00','0',null,null,null,null,'seed_demo',sysdate(),'seed_demo','标准化演示'),
('Edu_EducationMaterial',20004,'赵敏','teacher','数学与统计学院','学习经历-赵敏',
 '{"学校名称":"华中科技大学","专业":"应用数学","学历层次":"博士","学位":"理学博士"}',
 '/profile/upload/demo/m5.pdf','2026-01-24 15:05:00','0',null,null,null,null,'seed_demo',sysdate(),'seed_demo','标准化演示'),
('Staff_HonorMaterial',20005,'陈杰','teacher','外国语学院','荣誉称号-教学名师',
 '{"荣誉名称":"校级教学名师","获得日期":"2026-02-14","授予单位":"学校教务处","荣誉级别":"校级"}',
 '/profile/upload/demo/m6.pdf','2026-02-14 09:40:00','1',1,'管理员','2026-02-16 10:00:00','通过','seed_demo',sysdate(),'seed_demo','标准化演示'),
('Staff_StartupMaterial',20008,'刘洋','teacher','法学院','创业情况-法务咨询公司',
 '{"公司名称":"法擎咨询有限公司","法人":"刘洋","成立时间":"2024-06-01","所属行业":"法律咨询","持股比例":"35%","状态":"运营"}',
 '/profile/upload/demo/m7.pdf','2026-04-03 09:25:00','1',1,'管理员','2026-04-05 11:35:00','通过','seed_demo',sysdate(),'seed_demo','标准化演示'),
('Staff_PartTimeMaterial',20003,'王强','teacher','经济管理学院','兼职情况-产业导师',
 '{"兼职单位":"某科技园","兼职职位":"产业导师","研究方向":"产业数字化","起始时间":"2025-09-01","是否在职":"是"}',
 '/profile/upload/demo/m8.pdf','2025-12-02 11:20:00','2',1,'管理员','2025-12-04 16:40:00','补充证明','seed_demo',sysdate(),'seed_demo','标准化演示'),
('Staff_NewEntryMaterial',20007,'孙浩','teacher','体育学院','新入职教师记录-孙浩',
 '{"入职日期":"2025-08-20","转正日期":"2026-02-20","教师类别":"专任","入职方式":"招聘","是否博士":"否"}',
 '/profile/upload/demo/m9.pdf','2026-03-21 13:30:00','2',1,'管理员','2026-03-22 14:20:00','材料缺失','seed_demo',sysdate(),'seed_demo','标准化演示'),
('Staff_SpecialAppointmentMaterial',20006,'周婷','teacher','信息工程学院','特聘教师记录-讲座教授',
 '{"特聘层次":"讲座教授","合同开始日期":"2026-01-01","合同结束日期":"2027-12-31","聘用单位":"信息工程学院"}',
 '/profile/upload/demo/m10.pdf','2026-03-11 09:00:00','1',1,'管理员','2026-03-12 10:00:00','通过','seed_demo',sysdate(),'seed_demo','标准化演示'),
('Staff_AssessmentMaterial',20001,'张伟','teacher','计算机学院','考核结果-2025年度',
 '{"考核年份":"2025","考核日期":"2025-12-20","考核结果":"优秀","考核单位":"人事处"}',
 '/profile/upload/demo/m11.pdf','2025-12-20 15:00:00','1',1,'管理员','2025-12-22 09:20:00','通过','seed_demo',sysdate(),'seed_demo','标准化演示'),
('Staff_FamilyMaterial',20002,'李娜','teacher','机械工程学院','妇幼信息-李娜',
 '{"子女姓名":"李小雨","出生日期":"2019-04-03","是否在学":"是","备注":"幼儿园阶段"}',
 '/profile/upload/demo/m12.pdf','2026-02-10 11:50:00','0',null,null,null,null,'seed_demo',sysdate(),'seed_demo','标准化演示');

-- 5) Minimal field-definition demo (optional, for future dynamic forms)
insert into biz_material_field
(category_code, field_code, field_name, field_type, required_flag, dict_options, sort_num, stat_tag, status, create_by, create_time, update_by)
values
('Edu_CompetitionMaterial','competition_name','竞赛名称','string','1',null,1,'competition_name','0','seed_demo',sysdate(),'seed_demo'),
('Edu_CompetitionMaterial','competition_level','竞赛级别','select','1','国赛,省赛,校赛',2,'competition_level','0','seed_demo',sysdate(),'seed_demo'),
('Res_ProjectMaterial','project_type','项目类别','select','1','国家级,省部级,横向',1,'project_type','0','seed_demo',sysdate(),'seed_demo'),
('Res_ProjectMaterial','project_amount','合同金额','number','0',null,2,'project_amount','0','seed_demo',sysdate(),'seed_demo'),
('Staff_AssessmentMaterial','assessment_result','考核结果','select','1','优秀,合格,不合格',1,'assessment_result','0','seed_demo',sysdate(),'seed_demo');

-- 6) Quick verification
select 'biz_teacher_material' as table_name, count(1) as cnt from biz_teacher_material where create_by = 'seed_demo'
union all
select 'biz_student_achievement' as table_name, count(1) as cnt from biz_student_achievement where create_by = 'seed_demo'
union all
select 'biz_material_record' as table_name, count(1) as cnt from biz_material_record where create_by = 'seed_demo'
union all
select 'biz_material_field' as table_name, count(1) as cnt from biz_material_field where create_by = 'seed_demo';

SET SQL_SAFE_UPDATES = 1;
