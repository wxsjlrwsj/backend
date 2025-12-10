package org.example.chaoxingsystem.teacher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 教师端应用入口：
 * - 扫描配置/安全/用户/教师模块代码
 * - 注意：需确保 MapperScan 覆盖教师模块的 Mapper（当前仅扫描 user 包，会导致 QuestionAuditMapper 未被注册）
 */
@SpringBootApplication(scanBasePackages = {
  "org.example.chaoxingsystem.config",
  "org.example.chaoxingsystem.security",
  "org.example.chaoxingsystem.user",
  "org.example.chaoxingsystem.teacher",
  "org.example.chaoxingsystem.admin"
})
@MapperScan({
  "org.example.chaoxingsystem.user",
  "org.example.chaoxingsystem.teacher",
  "org.example.chaoxingsystem.admin"
})
public class TeacherApplication {
  public static void main(String[] args) {
    SpringApplication.run(TeacherApplication.class, args);
  }
}
