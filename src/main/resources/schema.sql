CREATE TABLE IF NOT EXISTS users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  phone VARCHAR(20),
  password_hash VARCHAR(100) NOT NULL,
  user_type VARCHAR(20) NOT NULL,
  real_name VARCHAR(50) NOT NULL,
  avatar VARCHAR(255),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 题型字典
CREATE TABLE IF NOT EXISTS biz_question_type (
  type_id TINYINT PRIMARY KEY,
  type_name VARCHAR(32) NOT NULL,
  type_code VARCHAR(16) NOT NULL UNIQUE,
  answer_json JSON NOT NULL,
  config_json JSON NOT NULL,
  is_active TINYINT NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS system_modules (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  code VARCHAR(100) NOT NULL UNIQUE,
  category VARCHAR(50) NOT NULL,
  version VARCHAR(50) NOT NULL,
  enabled TINYINT(1) NOT NULL DEFAULT 1,
  show_in_menu TINYINT(1) NOT NULL DEFAULT 1,
  route_path VARCHAR(200) NOT NULL,
  allowed_roles VARCHAR(200) NOT NULL,
  dependencies VARCHAR(500) NOT NULL,
  description VARCHAR(500),
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 组织机构
CREATE TABLE IF NOT EXISTS sys_organization (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  parent_id BIGINT NULL,
  name VARCHAR(100) NOT NULL,
  code VARCHAR(50) NOT NULL UNIQUE,
  type VARCHAR(20) NOT NULL,
  sort_order INT NOT NULL DEFAULT 1,
  path VARCHAR(255) NULL,
  status TINYINT NOT NULL DEFAULT 1,
  leader VARCHAR(50) NULL,
  phone VARCHAR(50) NULL,
  description VARCHAR(255) NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 学生档案
CREATE TABLE IF NOT EXISTS biz_student (
  user_id BIGINT PRIMARY KEY,
  student_no VARCHAR(50) NOT NULL,
  real_name VARCHAR(50) NOT NULL,
  gender TINYINT NULL,
  class_id BIGINT NOT NULL,
  major_code VARCHAR(50) NOT NULL,
  enrollment_year INT NOT NULL,
  politics_status VARCHAR(20) NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 教师档案
CREATE TABLE IF NOT EXISTS biz_teacher (
  user_id BIGINT PRIMARY KEY,
  teacher_no VARCHAR(50) NOT NULL,
  real_name VARCHAR(50) NOT NULL,
  dept_id BIGINT NOT NULL,
  title VARCHAR(50) NULL,
  entry_date DATE NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- RBAC 角色
CREATE TABLE IF NOT EXISTS sys_role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  role_name VARCHAR(30) NOT NULL,
  role_key VARCHAR(30) NOT NULL UNIQUE,
  data_scope CHAR(1) NOT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  remark VARCHAR(500) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- RBAC 菜单（目录/菜单/按钮）
CREATE TABLE IF NOT EXISTS sys_menu (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  parent_id BIGINT NOT NULL DEFAULT 0,
  name VARCHAR(50) NOT NULL,
  type CHAR(1) NOT NULL,
  perms VARCHAR(100) NULL,
  path VARCHAR(200) NULL,
  component VARCHAR(200) NULL,
  icon VARCHAR(100) NULL,
  sort_order INT NOT NULL DEFAULT 1,
  visible TINYINT NOT NULL DEFAULT 1
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 用户-角色关联
CREATE TABLE IF NOT EXISTS sys_user_role (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 角色-菜单关联
CREATE TABLE IF NOT EXISTS sys_role_menu (
  role_id BIGINT NOT NULL,
  menu_id BIGINT NOT NULL,
  PRIMARY KEY (role_id, menu_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 操作日志
CREATE TABLE IF NOT EXISTS sys_oper_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(50) NULL,
  business_type INT NULL,
  method VARCHAR(100) NULL,
  request_method VARCHAR(10) NULL,
  oper_name VARCHAR(50) NULL,
  oper_url VARCHAR(255) NULL,
  oper_ip VARCHAR(128) NULL,
  oper_location VARCHAR(255) NULL,
  oper_param TEXT NULL,
  json_result TEXT NULL,
  status INT NULL,
  error_msg VARCHAR(2000) NULL,
  oper_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  cost_time BIGINT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

  -- 教师模块：题目审核
  CREATE TABLE IF NOT EXISTS biz_question_audit (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    submitter_id BIGINT NOT NULL,
    submit_time DATETIME NOT NULL,
    type_id TINYINT NOT NULL,
    content TEXT NOT NULL,
    answer TEXT NULL,
    difficulty TINYINT NULL,
    subject_id BIGINT NULL,
    status TINYINT NOT NULL DEFAULT 0,
    audit_comment VARCHAR(500) NULL,
    auditor_id BIGINT NULL,
    audit_time DATETIME NULL
  ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

  -- 题目
  CREATE TABLE IF NOT EXISTS biz_question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type_id TINYINT NOT NULL,
    content TEXT NOT NULL,
    options JSON NULL,
    answer JSON NOT NULL,
    analysis TEXT NULL,
    difficulty TINYINT NOT NULL,
    subject VARCHAR(50) NOT NULL,
    knowledge_points VARCHAR(255) NULL,
    file_id VARCHAR(64) NULL,
    creator_id BIGINT NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status TINYINT NOT NULL DEFAULT 1
  ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

  -- 试卷主表
  CREATE TABLE IF NOT EXISTS biz_paper (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    subject VARCHAR(50) NOT NULL,
    total_score INT NOT NULL DEFAULT 0,
    pass_score INT NOT NULL DEFAULT 60,
    question_count INT NOT NULL DEFAULT 0,
    status TINYINT NOT NULL DEFAULT 0,
    creator_id BIGINT NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

  -- 试卷-题目关联
  CREATE TABLE IF NOT EXISTS biz_paper_question (
    paper_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    score INT NOT NULL,
    sort_order INT NOT NULL,
    PRIMARY KEY (paper_id, question_id)
  ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

  -- 考试主表
  CREATE TABLE IF NOT EXISTS biz_exam (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    paper_id BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NULL,
    duration INT NOT NULL,
    status TINYINT NOT NULL DEFAULT 0,
    creator_id BIGINT NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

  -- 考试记录
  CREATE TABLE IF NOT EXISTS biz_exam_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    exam_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    score INT NULL,
    status TINYINT NOT NULL DEFAULT 0,
    start_time DATETIME NULL,
    submit_time DATETIME NULL
  ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

  -- 答题明细
  CREATE TABLE IF NOT EXISTS biz_exam_answer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    student_answer JSON NOT NULL,
    file_id VARCHAR(64) NULL,
    score INT NULL,
    is_correct TINYINT(1) NULL,
    comment VARCHAR(500) NULL
  ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
