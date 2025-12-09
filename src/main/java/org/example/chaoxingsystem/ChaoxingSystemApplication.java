package org.example.chaoxingsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.chaoxingsystem.user")
public class ChaoxingSystemApplication {
  public static void main(String[] args) {
    SpringApplication.run(ChaoxingSystemApplication.class, args);
  }
}