SET NAMES utf8mb4;
USE chaoxing;

-- 题型字典 5 条
INSERT IGNORE INTO biz_question_type(type_id,type_name,type_code,answer_json,config_json,is_active,create_time,update_time) VALUES
 (1,'单选题','SINGLE',CAST('[\"B\"]' AS JSON),CAST('{\"leak_score\":0.0,\"max_option\":5,\"is_objective\":true,\"requires_file_id\":false}' AS JSON),1,NOW(),NOW()),
 (2,'多选题','MULTI',CAST('[\"A\",\"C\"]' AS JSON),CAST('{\"leak_score\":0.5,\"max_option\":6,\"is_objective\":true,\"requires_file_id\":false}' AS JSON),1,NOW(),NOW()),
 (3,'判断题','TRUE_FALSE',CAST('true' AS JSON),CAST('{\"is_objective\":true,\"requires_file_id\":false}' AS JSON),1,NOW(),NOW()),
 (4,'填空题','FILL',CAST('[\"空1\",\"空2\"]' AS JSON),CAST('{\"is_objective\":false,\"requires_file_id\":false}' AS JSON),1,NOW(),NOW()),
 (5,'简答题','SHORT',CAST('"文本"' AS JSON),CAST('{\"is_objective\":false,\"requires_file_id\":false}' AS JSON),1,NOW(),NOW());

-- 系统模块 5 条
INSERT IGNORE INTO system_modules(name,code,category,version,enabled,show_in_menu,route_path,allowed_roles,dependencies,description) VALUES
 ('题目审核','tch_audit','teacher','1.0',1,1,'/audit','TEACHER,ADMIN','', '审核老师或学生试题'),
 ('题库管理','tch_bank','teacher','1.0',1,1,'/bank','TEACHER,ADMIN','', '维护系统题库'),
 ('试卷管理','tch_paper','teacher','1.0',1,1,'/papers','TEACHER,ADMIN','', '组卷与维护'),
 ('考试管理','tch_exam','teacher','1.0',1,1,'/exams','TEACHER,ADMIN','', '发布考试与监考'),
 ('成绩管理','tch_score','teacher','1.0',1,1,'/scores','TEACHER,ADMIN','', '阅卷与统计');

-- 组织 5 条
INSERT IGNORE INTO sys_organization(parent_id,name,code,type,sort_order,path,status,leader,phone,description) VALUES
(NULL,'信息学院','ORG-INFO','dept',1,'/信息学院',1,'张主任','0571-0000001','学院'),
(NULL,'计算机系','ORG-CS','dept',1,'/信息学院/计算机系',1,'李老师','0571-0000002','系'),
(NULL,'软件工程系','ORG-SE','dept',2,'/信息学院/软件工程系',1,'王老师','0571-0000003','系'),
(NULL,'机电学院','ORG-MECH','dept',2,'/机电学院',1,'赵主任','0571-0000004','学院'),
(NULL,'自动化系','ORG-AUTO','dept',1,'/机电学院/自动化系',1,'钱老师','0571-0000005','系');

-- 班级补齐，确保学生档案 class_id 有对应机构
SET @org_cs_id = (SELECT id FROM sys_organization WHERE code='ORG-CS');
SET @org_se_id = (SELECT id FROM sys_organization WHERE code='ORG-SE');
INSERT IGNORE INTO sys_organization(id,parent_id,name,code,type,sort_order,path,status,leader,phone,description) VALUES
 (100,@org_cs_id,'计科一班','CLS-CS-101','class',1,'/信息学院/计算机系/计科一班',1,'班主任甲','0571-1000001','班级'),
 (101,@org_cs_id,'计科二班','CLS-CS-102','class',2,'/信息学院/计算机系/计科二班',1,'班主任乙','0571-1000002','班级'),
 (102,@org_se_id,'软工一班','CLS-SE-101','class',1,'/信息学院/软件工程系/软工一班',1,'班主任丙','0571-1000003','班级');

-- 初始化基础用户（仅当不存在时插入），供档案引用
INSERT IGNORE INTO users(username,email,phone,password_hash,user_type,real_name,avatar) VALUES
 ('admin1','admin1@example.com',NULL,'$2a$10$zJpIYkPp7OLoOqMZ9o4m3uQV1YVQdGmJ8VxgQf3o3PzF8q6QJk7uS','admin','管理员一',NULL),
 ('teacher1','teacher1@example.com',NULL,'$2a$10$zJpIYkPp7OLoOqMZ9o4m3uQV1YVQdGmJ8VxgQf3o3PzF8q6QJk7uS','teacher','教师一',NULL),
 ('teacher2','teacher2@example.com',NULL,'$2a$10$zJpIYkPp7OLoOqMZ9o4m3uQV1YVQdGmJ8VxgQf3o3PzF8q6QJk7uS','teacher','教师二',NULL),
 ('teacher3','teacher3@example.com',NULL,'$2a$10$zJpIYkPp7OLoOqMZ9o4m3uQV1YVQdGmJ8VxgQf3o3PzF8q6QJk7uS','teacher','教师三',NULL),
 ('teacher4','teacher4@example.com',NULL,'$2a$10$zJpIYkPp7OLoOqMZ9o4m3uQV1YVQdGmJ8VxgQf3o3PzF8q6QJk7uS','teacher','教师四',NULL),
 ('teacher5','teacher5@example.com',NULL,'$2a$10$zJpIYkPp7OLoOqMZ9o4m3uQV1YVQdGmJ8VxgQf3o3PzF8q6QJk7uS','teacher','教师五',NULL),
 ('student1','student1@example.com',NULL,'$2a$10$zJpIYkPp7OLoOqMZ9o4m3uQV1YVQdGmJ8VxgQf3o3PzF8q6QJk7uS','student','学生一',NULL),
 ('student2','student2@example.com',NULL,'$2a$10$zJpIYkPp7OLoOqMZ9o4m3uQV1YVQdGmJ8VxgQf3o3PzF8q6QJk7uS','student','学生二',NULL),
 ('student3','student3@example.com',NULL,'$2a$10$zJpIYkPp7OLoOqMZ9o4m3uQV1YVQdGmJ8VxgQf3o3PzF8q6QJk7uS','student','学生三',NULL),
 ('student4','student4@example.com',NULL,'$2a$10$zJpIYkPp7OLoOqMZ9o4m3uQV1YVQdGmJ8VxgQf3o3PzF8q6QJk7uS','student','学生四',NULL),
 ('test','test@example.com',NULL,'$2a$10$zJpIYkPp7OLoOqMZ9o4m3uQV1YVQdGmJ8VxgQf3o3PzF8q6QJk7uS','student','测试生',NULL);

-- 角色 5 条
INSERT IGNORE INTO sys_role(role_name,role_key,data_scope,status,remark) VALUES
 ('管理员','ADMIN','1',1,'系统管理员'),
 ('教师','TEACHER','1',1,'教师角色'),
 ('学生','STUDENT','1',1,'学生角色'),
 ('监考','MONITOR','1',1,'监考角色'),
 ('审核员','AUDITOR','1',1,'审核角色');

-- 菜单 5 条
INSERT IGNORE INTO sys_menu(parent_id,name,type,perms,path,component,icon,sort_order,visible) VALUES
 (0,'题库管理','M','bank:view','/bank','teacher/Bank.vue','Collection',1,1),
 (0,'试卷管理','M','paper:view','/papers','teacher/Paper.vue','Document',2,1),
 (0,'考试管理','M','exam:view','/exams','teacher/Exam.vue','Timer',3,1),
 (0,'成绩管理','M','score:view','/scores','teacher/Score.vue','Histogram',4,1),
 (0,'审核管理','M','audit:view','/audit','teacher/Audit.vue','CircleCheck',5,1);

-- 用户-角色映射（按类型自动）
INSERT IGNORE INTO sys_user_role(user_id,role_id)
 SELECT u.id, r.id FROM users u JOIN sys_role r ON (
   (u.user_type='admin' AND r.role_key='ADMIN') OR
   (u.user_type='teacher' AND r.role_key='TEACHER') OR
   (u.user_type='student' AND r.role_key='STUDENT')
 );

-- 角色-菜单映射（ADMIN/TEACHER 有所有教学菜单）
INSERT IGNORE INTO sys_role_menu(role_id,menu_id)
 SELECT r.id, m.id FROM sys_role r JOIN sys_menu m ON (
   (r.role_key IN ('ADMIN','TEACHER') AND m.perms IN ('bank:view','paper:view','exam:view','score:view','audit:view'))
 );

-- 教师与学生档案 5 条各
INSERT IGNORE INTO biz_teacher(user_id,teacher_no,real_name,dept_id,title,entry_date) VALUES
 ((SELECT id FROM users WHERE username='teacher1'),'T2023001','教师一',(SELECT id FROM sys_organization WHERE code='ORG-CS'),'讲师','2023-09-01'),
 ((SELECT id FROM users WHERE username='teacher2'),'T2023002','教师二',(SELECT id FROM sys_organization WHERE code='ORG-SE'),'副教授','2022-03-01'),
 ((SELECT id FROM users WHERE username='teacher3'),'T2023003','教师三',(SELECT id FROM sys_organization WHERE code='ORG-CS'),'讲师','2021-06-01'),
 ((SELECT id FROM users WHERE username='teacher4'),'T2023004','教师四',(SELECT id FROM sys_organization WHERE code='ORG-CS'),'教授','2019-01-01'),
 ((SELECT id FROM users WHERE username='teacher5'),'T2023005','教师五',(SELECT id FROM sys_organization WHERE code='ORG-SE'),'讲师','2020-10-01');

INSERT IGNORE INTO biz_student(user_id,student_no,real_name,gender,class_id,major_code,enrollment_year,politics_status) VALUES
 ((SELECT id FROM users WHERE username='test'),'S2023000','测试生',1,100,'CS',2023,'群众'),
 ((SELECT id FROM users WHERE username='student1'),'S2023001','张三',1,101,'CS',2023,'群众'),
 ((SELECT id FROM users WHERE username='student2'),'S2023002','李四',1,101,'CS',2023,'团员'),
 ((SELECT id FROM users WHERE username='student3'),'S2023003','王五',1,102,'SE',2022,'群众'),
 ((SELECT id FROM users WHERE username='student4'),'S2023004','赵六',0,102,'SE',2022,'团员');

-- 题目 5 条
INSERT IGNORE INTO biz_question(type_id,content,options,answer,analysis,difficulty,subject,knowledge_points,file_id,creator_id,status,create_time)
 VALUES
 (1,'Java中int占用几个字节？',CAST('[{"key":"A","value":"2"},{"key":"B","value":"4"},{"key":"C","value":"8"}]' AS JSON),CAST('["B"]' AS JSON),'基础数据类型',1,'Java','数据类型',NULL,(SELECT id FROM users WHERE username='teacher1'),1,NOW()),
 (2,'下列哪些是JVM组成？',CAST('[{"key":"A","value":"类加载器"},{"key":"B","value":"执行引擎"},{"key":"C","value":"垃圾回收"},{"key":"D","value":"IDE"}]' AS JSON),CAST('["A","B","C"]' AS JSON),'了解JVM',2,'Java','JVM,内存',NULL,(SELECT id FROM users WHERE username='teacher2'),1,NOW()),
 (3,'HTTP是无状态协议，对吗？',NULL,CAST('true' AS JSON),'协议特性',1,'网络','HTTP',NULL,(SELECT id FROM users WHERE username='teacher3'),1,NOW()),
 (4,'请填写Java中字符串不可变的原因',NULL,CAST('["字符串池","线程安全","类设计"]' AS JSON),NULL,3,'Java','String',NULL,(SELECT id FROM users WHERE username='teacher4'),1,NOW()),
 (5,'简述垃圾回收的标记-清除算法',NULL,CAST('"先标记后清除"' AS JSON),NULL,3,'Java','GC',NULL,(SELECT id FROM users WHERE username='teacher5'),1,NOW());

-- 试卷 5 条
INSERT IGNORE INTO biz_paper(name,subject,total_score,pass_score,question_count,status,creator_id)
 VALUES
 ('Java基础试卷1','Java',100,60,5,1,(SELECT id FROM users WHERE username='teacher1')),
 ('Java基础试卷2','Java',100,60,5,0,(SELECT id FROM users WHERE username='teacher2')),
 ('网络基础试卷1','网络',100,60,5,0,(SELECT id FROM users WHERE username='teacher3')),
 ('算法基础试卷1','算法',100,60,5,0,(SELECT id FROM users WHERE username='teacher4')),
 ('操作系统试卷1','操作系统',100,60,5,0,(SELECT id FROM users WHERE username='teacher5'));

-- 试卷题目关联（第一张试卷关联前5题）
INSERT IGNORE INTO biz_paper_question(paper_id,question_id,score,sort_order)
 SELECT (SELECT MIN(id) FROM biz_paper WHERE name='Java基础试卷1'), q.id, 20, ROW_NUMBER() OVER (ORDER BY q.id)
 FROM (SELECT id FROM biz_question ORDER BY id LIMIT 5) q;

-- 考试 5 条
INSERT IGNORE INTO biz_exam(name,paper_id,start_time,end_time,duration,status,creator_id)
 VALUES
 ('Java期末考试1',(SELECT id FROM biz_paper WHERE name='Java基础试卷1'),'2025-12-20 09:00:00','2025-12-20 11:00:00',120,1,(SELECT id FROM users WHERE username='teacher1')),
 ('Java期末考试2',(SELECT id FROM biz_paper WHERE name='Java基础试卷2'),'2025-12-21 09:00:00','2025-12-21 11:00:00',120,0,(SELECT id FROM users WHERE username='teacher2')),
 ('网络期末考试1',(SELECT id FROM biz_paper WHERE name='网络基础试卷1'),'2025-12-22 09:00:00','2025-12-22 11:00:00',120,0,(SELECT id FROM users WHERE username='teacher3')),
 ('算法期末考试1',(SELECT id FROM biz_paper WHERE name='算法基础试卷1'),'2025-12-23 09:00:00','2025-12-23 11:00:00',120,0,(SELECT id FROM users WHERE username='teacher4')),
 ('操作系统期末考试1',(SELECT id FROM biz_paper WHERE name='操作系统试卷1'),'2025-12-24 09:00:00','2025-12-24 11:00:00',120,0,(SELECT id FROM users WHERE username='teacher5'));

-- 考试记录 5 条
INSERT IGNORE INTO biz_exam_record(exam_id,student_id,score,status,start_time,submit_time)
 VALUES
 ((SELECT id FROM biz_exam WHERE name='Java期末考试1'),(SELECT id FROM users WHERE username='student1'),NULL,0,NOW(),NULL),
 ((SELECT id FROM biz_exam WHERE name='Java期末考试1'),(SELECT id FROM users WHERE username='student2'),NULL,0,NOW(),NULL),
 ((SELECT id FROM biz_exam WHERE name='Java期末考试2'),(SELECT id FROM users WHERE username='student3'),NULL,0,NOW(),NULL),
 ((SELECT id FROM biz_exam WHERE name='网络期末考试1'),(SELECT id FROM users WHERE username='student1'),NULL,0,NOW(),NULL),
 ((SELECT id FROM biz_exam WHERE name='算法期末考试1'),(SELECT id FROM users WHERE username='student2'),NULL,0,NOW(),NULL);

-- 答题明细 5 条
INSERT IGNORE INTO biz_exam_answer(record_id,question_id,student_answer,score,is_correct,comment)
 SELECT r.id, q.id, CAST('["A"]' AS JSON), NULL, NULL, NULL
 FROM (SELECT id FROM biz_exam_record ORDER BY id LIMIT 5) r
 JOIN (SELECT id FROM biz_question ORDER BY id LIMIT 1) q;

-- 审核记录 5 条
INSERT IGNORE INTO biz_question_audit(submitter_id,submit_time,type_id,content,answer,difficulty,subject_id,status,audit_comment,auditor_id,audit_time)
 VALUES
 ((SELECT id FROM users WHERE username='teacher1'),NOW(),1,'{"stem":"题干1"}','参考答案1',2,1,1,'通过',(SELECT id FROM users WHERE username='admin1'),NOW()),
 ((SELECT id FROM users WHERE username='teacher2'),NOW(),2,'{"stem":"题干2"}','参考答案2',3,1,2,'描述不清',NULL,NULL),
 ((SELECT id FROM users WHERE username='student1'),NOW(),1,'{"stem":"题干3"}','参考答案3',1,1,0,NULL,NULL,NULL),
 ((SELECT id FROM users WHERE username='teacher3'),NOW(),3,'{"stem":"题干4"}','参考答案4',2,1,1,'通过',(SELECT id FROM users WHERE username='admin1'),NOW()),
 ((SELECT id FROM users WHERE username='student2'),NOW(),4,'{"stem":"题干5"}','参考答案5',3,1,2,'不符合规范',NULL,NULL);

-- 操作日志 5 条
INSERT IGNORE INTO sys_oper_log(title,business_type,method,request_method,oper_name,oper_url,oper_ip,oper_location,oper_param,json_result,status,error_msg,cost_time)
 VALUES
 ('登录',1,'AuthController.login','POST','admin1','/api/auth/login','127.0.0.1','本地','{"u":"admin1"}','{"code":200}',0,NULL,12),
 ('注册',1,'AuthController.register','POST','student1','/api/auth/register','127.0.0.1','本地','{"u":"student1"}','{"code":200}',0,NULL,20),
 ('获取题目列表',1,'QuestionBankController.list','GET','teacher1','/api/questions','127.0.0.1','本地','{}','{"code":200}',0,NULL,8),
 ('创建试卷',1,'PaperController.create','POST','teacher1','/api/papers','127.0.0.1','本地','{}','{"code":200}',0,NULL,15),
 ('发布考试',1,'ExamController.create','POST','teacher1','/api/exams','127.0.0.1','本地','{}','{"code":200}',0,NULL,16);
