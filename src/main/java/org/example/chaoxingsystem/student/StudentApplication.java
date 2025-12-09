package org.example.chaoxingsystem.student;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
