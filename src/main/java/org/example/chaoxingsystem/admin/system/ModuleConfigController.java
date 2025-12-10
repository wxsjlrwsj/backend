package org.example.chaoxingsystem.admin.system;

import org.example.chaoxingsystem.admin.module.SystemModuleMapper;
import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/** 返回禁用模块配置，用于前端初始化 */
@RestController
@RequestMapping("/api/system")
public class ModuleConfigController {
  private final SystemModuleMapper mapper;
  public ModuleConfigController(SystemModuleMapper mapper) { this.mapper = mapper; }

  @GetMapping("/module-config")
  @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
  public ResponseEntity<ApiResponse<List<String>>> disabledModules() {
    // 拉取全部模块后筛选出禁用的编码列表
    var all = mapper.selectPage(null, null, null, 0, Integer.MAX_VALUE);
    var disabled = all.stream().filter(m -> Boolean.FALSE.equals(m.getEnabled())).map(m -> m.getCode()).collect(Collectors.toList());
    return ResponseEntity.ok(ApiResponse.success("获取成功", disabled));
  }
}
