package org.example.chaoxingsystem.config;

import org.example.chaoxingsystem.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.beans.factory.annotation.Value;

/**
 * 安全配置：
 * - 关闭 CSRF，启用无状态会话
 * - 配置 CORS 允许来源
 * - 放行登录/注册/重置密码，其余接口按角色鉴权
 * - 在用户名密码过滤器之前加入 JWT 认证过滤器
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
    http.csrf(csrf -> csrf.disable());
    http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
    http.authorizeHttpRequests(auth -> auth
      .requestMatchers(HttpMethod.POST, "/api/login", "/api/register", "/api/reset-password").permitAll()
      .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/register", "/api/auth/reset-password").permitAll()
      .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
      .anyRequest().authenticated()
    );
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Value("${security.cors.allowed-origins:*}")
  private String allowedOrigins;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    // 允许来自配置的来源进行跨域访问，支持通配符
    CorsConfiguration config = new CorsConfiguration();
    for (String origin : allowedOrigins.split(",")) {
      String o = origin.trim();
      if (!o.isEmpty()) config.addAllowedOriginPattern(o);
    }
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
