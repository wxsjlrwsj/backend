package org.example.chaoxingsystem.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
  "org.example.chaoxingsystem.config",
  "org.example.chaoxingsystem.security",
  "org.example.chaoxingsystem.user",
  "org.example.chaoxingsystem.admin"
})
@MapperScan("org.example.chaoxingsystem.user")
public class AdminApplication {
  public static void main(String[] args) {
    SpringApplication.run(AdminApplication.class, args);
  }
}
