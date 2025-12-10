package org.example.chaoxingsystem.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 管理端应用入口：
 * - 扫描配置/安全/用户/管理模块代码
 * - MapperScan 扫描整个项目下的 Mapper 接口
 */
@SpringBootApplication(scanBasePackages = {
  "org.example.chaoxingsystem.config",
  "org.example.chaoxingsystem.security",
  "org.example.chaoxingsystem.user",
  "org.example.chaoxingsystem.admin"
})
@MapperScan("org.example.chaoxingsystem")
public class AdminApplication {
  public static void main(String[] args) {
    SpringApplication.run(AdminApplication.class, args);
  }
}
