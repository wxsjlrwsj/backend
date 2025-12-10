package org.example.chaoxingsystem.config;

import java.lang.annotation.*;

/** 模块状态检查注解：当模块被禁用时拦截请求 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModuleCheck {
  String moduleCode();
}
