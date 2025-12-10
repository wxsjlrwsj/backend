package org.example.chaoxingsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 注册模块检查拦截器 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  private final ModuleCheckInterceptor interceptor;
  public WebMvcConfig(ModuleCheckInterceptor interceptor) { this.interceptor = interceptor; }
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 全局拦截，读取 @ModuleCheck 注解以判断模块启用状态
    registry.addInterceptor(interceptor).addPathPatterns("/**");
  }
}
