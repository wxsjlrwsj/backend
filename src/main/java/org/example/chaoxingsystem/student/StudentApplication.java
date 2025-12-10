package org.example.chaoxingsystem.student;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 学生端应用入口：
 * - 扫描配置/安全/用户/学生模块代码
 * - MapperScan 仅扫描用户模块 Mapper（当前学生端仅依赖用户表）
 */
@SpringBootApplication(scanBasePackages = {
  "org.example.chaoxingsystem.config",
  "org.example.chaoxingsystem.security",
  "org.example.chaoxingsystem.user",
  "org.example.chaoxingsystem.student"
})
@MapperScan("org.example.chaoxingsystem.user")
public class StudentApplication {
  public static void main(String[] args) {
    SpringApplication.run(StudentApplication.class, args);
  }
}
