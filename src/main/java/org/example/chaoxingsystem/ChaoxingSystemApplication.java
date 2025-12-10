package org.example.chaoxingsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 聚合启动入口：用于在开发/测试中一次性启动整个系统
 */
@SpringBootApplication(scanBasePackages = {"org.example.chaoxingsystem"})
@MapperScan("org.example.chaoxingsystem")
public class ChaoxingSystemApplication {
  public static void main(String[] args) {
    SpringApplication.run(ChaoxingSystemApplication.class, args);
  }
}
