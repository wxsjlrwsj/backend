# 演示数据填充（每表 5 条）

说明：以下为各表的 5 条示例数据与对应 SQL；已按业务关联关系构造，前端列表页即可显示。

## 使用方法
- 将本仓库中的 `backend/seed.sql` 导入到 MySQL：
  - 容器环境：`docker cp backend/seed.sql chaoxing-mysql:/seed.sql && docker exec chaoxing-mysql mysql -uroot -proot chaoxing < /seed.sql`
  - 本机环境：`mysql -uroot -p -D chaoxing < backend/seed.sql`

## 涵盖表
- `users`、`biz_teacher`、`biz_student`
- `biz_question_type`、`biz_question`
- `biz_paper`、`biz_paper_question`
- `biz_exam`、`biz_exam_record`、`biz_exam_answer`
- `biz_question_audit`
- `system_modules`、`sys_organization`
- `sys_role`、`sys_menu`、`sys_user_role`、`sys_role_menu`、`sys_oper_log`

详细 SQL 请见 `backend/seed.sql` 文件。

