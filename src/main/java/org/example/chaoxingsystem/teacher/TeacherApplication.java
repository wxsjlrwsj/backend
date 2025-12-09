package org.example.chaoxingsystem.teacher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
  "org.example.chaoxingsystem.config",
  "org.example.chaoxingsystem.security",
  "org.example.chaoxingsystem.user",
  "org.example.chaoxingsystem.teacher"
})
@MapperScan("org.example.chaoxingsystem.user")
public class TeacherApplication {
  public static void main(String[] args) {
    SpringApplication.run(TeacherApplication.class, args);
  }
}
