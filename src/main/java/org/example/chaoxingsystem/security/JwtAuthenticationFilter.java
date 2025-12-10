package org.example.chaoxingsystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.chaoxingsystem.user.TokenService;
import org.example.chaoxingsystem.user.TokenService.TokenData;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT 认证过滤器：
 * - 从请求头读取 Authorization: Bearer <token>
 * - 解析并校验自定义 JWT，提取用户名与用户类型
 * - 基于用户类型映射 Spring Security 角色（ROLE_STUDENT/ROLE_TEACHER/ROLE_ADMIN）
 * - 设置认证上下文后放行
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final TokenService tokenService;

  public JwtAuthenticationFilter(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      TokenData data = tokenService.parseAndValidate(token);
      if (data != null) {
        String role = "ROLE_" + data.getUserType().toUpperCase();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          data.getUsername(), null, Collections.singletonList(new SimpleGrantedAuthority(role))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }
}
