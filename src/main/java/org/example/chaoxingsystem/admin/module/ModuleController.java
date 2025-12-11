package org.example.chaoxingsystem.admin.module;

import jakarta.validation.Valid;
import org.example.chaoxingsystem.admin.module.dto.ModuleItemResponse;
import org.example.chaoxingsystem.admin.module.dto.ModuleListResponse;
import org.example.chaoxingsystem.admin.module.dto.ModuleRequest;
import org.example.chaoxingsystem.admin.module.dto.ModuleStatusRequest;
import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统模块配置接口：分页查询、创建/更新/删除、启用禁用、字典查询
 */
@RestController
@RequestMapping("/api/system")
public class ModuleController {
  private final SystemModuleService service;

  public ModuleController(SystemModuleService service) {
    this.service = service;
  }

  @GetMapping("/modules")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<ModuleListResponse>> list(
      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
      @RequestParam(value = "size", required = false, defaultValue = "10") int size,
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "category", required = false) String category,
      @RequestParam(value = "status", required = false) String status
  ) {
    Boolean enabled = null;
    if ("enabled".equalsIgnoreCase(status)) enabled = true;
    if ("disabled".equalsIgnoreCase(status)) enabled = false;
    long total = service.count(keyword, category, enabled);
    var items = service.page(keyword, category, enabled, page, size).stream().map(m ->
      new ModuleItemResponse(
        m.getId(), m.getName(), m.getCode(), m.getCategory(), m.getVersion(), m.getEnabled(), m.getShowInMenu(), m.getRoutePath(),
        SystemModuleService.split(m.getAllowedRoles()), SystemModuleService.split(m.getDependencies()), m.getDescription(), m.getUpdatedAt()
      )
    ).collect(Collectors.toList());
    var data = new ModuleListResponse(items, total);
    return ResponseEntity.ok(ApiResponse.success("查询成功", data));
  }

  @GetMapping("/modules/visible")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ApiResponse<List<ModuleItemResponse>>> visibleByRole(
      @RequestParam("role") String role
  ) {
    var items = service.visibleByRole(role).stream().map(m ->
      new ModuleItemResponse(
        m.getId(), m.getName(), m.getCode(), m.getCategory(), m.getVersion(), m.getEnabled(), m.getShowInMenu(), m.getRoutePath(),
        SystemModuleService.split(m.getAllowedRoles()), SystemModuleService.split(m.getDependencies()), m.getDescription(), m.getUpdatedAt()
      )
    ).collect(Collectors.toList());
    return ResponseEntity.ok(ApiResponse.success("获取成功", items));
  }

  @PostMapping("/modules")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody ModuleRequest req) {
    SystemModule m = new SystemModule();
    m.setName(req.getName());
    m.setCode(req.getCode());
    m.setCategory(req.getCategory());
    m.setVersion(req.getVersion());
    m.setEnabled(req.getEnabled());
    m.setShowInMenu(req.getShowInMenu());
    m.setRoutePath(req.getRoutePath());
    m.setAllowedRoles(SystemModuleService.join(req.getAllowedRoles()));
    m.setDependencies(SystemModuleService.join(req.getDependencies()));
    m.setDescription(req.getDescription());
    service.create(m);
    return ResponseEntity.ok(ApiResponse.success("新增成功", null));
  }

  @PutMapping("/modules/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> update(@PathVariable("id") Long id, @Valid @RequestBody ModuleRequest req) {
    SystemModule m = new SystemModule();
    m.setName(req.getName());
    m.setCode(req.getCode());
    m.setCategory(req.getCategory());
    m.setVersion(req.getVersion());
    m.setEnabled(req.getEnabled());
    m.setShowInMenu(req.getShowInMenu());
    m.setRoutePath(req.getRoutePath());
    m.setAllowedRoles(SystemModuleService.join(req.getAllowedRoles()));
    m.setDependencies(SystemModuleService.join(req.getDependencies()));
    m.setDescription(req.getDescription());
    service.update(id, m);
    return ResponseEntity.ok(ApiResponse.success("编辑成功", null));
  }

  @DeleteMapping("/modules/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
    service.delete(id);
    return ResponseEntity.ok(ApiResponse.success("删除成功", null));
  }

  @PatchMapping("/modules/{id}/status")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> switchStatus(@PathVariable("id") Long id, @Valid @RequestBody ModuleStatusRequest req) {
    service.switchStatus(id, req.getEnabled());
    return ResponseEntity.ok(ApiResponse.success("状态更新成功", null));
  }

  @GetMapping("/dictionaries/module-categories")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<String>>> categories() {
    var categories = List.of("exam", "question", "system", "monitor", "org");
    return ResponseEntity.ok(ApiResponse.success("获取成功", categories));
  }

  @GetMapping("/dictionaries/roles")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<String>>> roles() {
    var roles = List.of("student", "teacher", "admin");
    return ResponseEntity.ok(ApiResponse.success("获取成功", roles));
  }
}
