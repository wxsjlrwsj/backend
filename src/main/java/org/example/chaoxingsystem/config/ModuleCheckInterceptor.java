package org.example.chaoxingsystem.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.chaoxingsystem.admin.module.SystemModuleMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/** 读取 @ModuleCheck 注解并校验模块是否启用 */
@Component
public class ModuleCheckInterceptor implements HandlerInterceptor {
  private final SystemModuleMapper moduleMapper;

  public ModuleCheckInterceptor(SystemModuleMapper moduleMapper) {
    this.moduleMapper = moduleMapper;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (handler instanceof HandlerMethod hm) {
      ModuleCheck onMethod = hm.getMethodAnnotation(ModuleCheck.class);
      ModuleCheck onType = hm.getBeanType().getAnnotation(ModuleCheck.class);
      ModuleCheck anno = onMethod != null ? onMethod : onType;
      if (anno != null) {
        var code = anno.moduleCode();
        // 若未配置该模块，视为启用；否则根据 enabled 状态拦截
        var count = moduleMapper.countByCode(code);
        if (count == null || count == 0) {
          return true; // 未配置即视为启用
        }
        var module = moduleMapper.selectByCode(code);
        if (module != null && Boolean.FALSE.equals(module.getEnabled())) {
          response.setStatus(403);
          response.setContentType("application/json;charset=UTF-8");
          response.getWriter().write("{\"code\":403,\"message\":\"该功能模块已被禁用\",\"data\":null}");
          return false;
        }
      }
    }
    return true;
  }
}
