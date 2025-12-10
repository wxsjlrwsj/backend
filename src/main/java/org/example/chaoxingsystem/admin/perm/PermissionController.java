package org.example.chaoxingsystem.admin.perm;

import org.example.chaoxingsystem.user.UserService;
import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限与菜单相关接口：
 * - 路由树按角色聚合生成，前端使用 meta.roles 控制显示
 * - 角色的增删改查、用户-角色授权、角色-菜单授权
 */
@RestController
@RequestMapping("/api/system")
public class PermissionController {
  private final MenuMapper menuMapper;
  private final RoleMapper roleMapper;
  private final UserRoleMapper userRoleMapper;
  private final UserService userService;

  public PermissionController(MenuMapper menuMapper, RoleMapper roleMapper, UserRoleMapper userRoleMapper, UserService userService) {
    this.menuMapper = menuMapper;
    this.roleMapper = roleMapper;
    this.userRoleMapper = userRoleMapper;
    this.userService = userService;
  }

  /** 获取当前用户的菜单路由树 */
  @GetMapping("/menu/routes")
  @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
  public ResponseEntity<ApiResponse<List<Map<String, Object>>>> routes(Authentication auth) {
    var user = userService.getByUsername(auth.getName());
    List<Long> roleIds = userRoleMapper.selectRoleIdsByUserId(user.getId());
    // 若未显式分配角色，则根据 userType 推断
    if (roleIds.isEmpty()) {
      String key = user.getUserType().toLowerCase(Locale.ROOT);
      var candidate = roleMapper.selectAll().stream().filter(r -> key.equals(r.getRoleKey())).findFirst();
      if (candidate.isPresent()) roleIds = List.of(candidate.get().getId());
    }
    List<Menu> menus = new ArrayList<>();
    for (Long rid : roleIds) menus.addAll(menuMapper.selectByRoleId(rid));
    Map<Long, List<Menu>> byParent = menus.stream().collect(Collectors.groupingBy(m -> m.getParentId()));
    // 收集角色字符用于前端 meta.roles
    List<String> roleKeys = roleIds.stream()
      .map(roleMapper::selectById)
      .filter(Objects::nonNull)
      .map(Role::getRoleKey)
      .toList();
    List<Map<String, Object>> tree = buildMenuTree(byParent, 0L, roleKeys);
    return ResponseEntity.ok(ApiResponse.success("获取成功", tree));
  }

  private List<Map<String, Object>> buildMenuTree(Map<Long, List<Menu>> byParent, Long parentId, List<String> roleKeys) {
    return byParent.getOrDefault(parentId, List.of()).stream().map(m -> {
      Map<String, Object> node = new LinkedHashMap<>();
      node.put("name", m.getName());
      node.put("path", m.getPath());
      node.put("hidden", m.getVisible() != null && m.getVisible() == 0);
      if (m.getComponent() != null) node.put("component", m.getComponent());
      Map<String, Object> meta = new LinkedHashMap<>();
      meta.put("title", m.getName());
      meta.put("icon", m.getIcon());
      meta.put("roles", roleKeys);
      node.put("meta", meta);
      node.put("children", buildMenuTree(byParent, m.getId(), roleKeys));
      return node;
    }).collect(Collectors.toList());
  }

  /** 角色列表 */
  @GetMapping("/role/list")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<Role>>> roleList(@RequestParam(value = "keyword", required = false) String keyword,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    int offset = (Math.max(pageNum, 1) - 1) * Math.max(pageSize, 1);
    var list = roleMapper.selectPage(keyword, offset, pageSize);
    return ResponseEntity.ok(ApiResponse.success("获取成功", list));
  }

  /** 新增角色 */
  @PostMapping("/role")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> addRole(@RequestBody Role r) {
    roleMapper.insert(r);
    return ResponseEntity.ok(ApiResponse.success("新增成功", null));
  }

  /** 修改角色 */
  @PutMapping("/role")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> updateRole(@RequestBody Role r) {
    roleMapper.updateById(r);
    return ResponseEntity.ok(ApiResponse.success("修改成功", null));
  }

  /** 删除角色 */
  @DeleteMapping("/role/{roleId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable("roleId") Long roleId) {
    roleMapper.deleteById(roleId);
    return ResponseEntity.ok(ApiResponse.success("删除成功", null));
  }

  /** 获取角色详情 */
  @GetMapping("/role/{roleId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Role>> roleDetail(@PathVariable("roleId") Long roleId) {
    var r = roleMapper.selectById(roleId);
    return ResponseEntity.ok(ApiResponse.success("获取成功", r));
  }

  /** 获取某角色下已分配的用户列表（仅返回 userId 列表，可扩展为详细信息） */
  @GetMapping("/role/{roleId}/allocated-users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<Long>>> allocatedUsers(@PathVariable("roleId") Long roleId) {
    var ids = userRoleMapper.selectUserIdsByRoleId(roleId);
    return ResponseEntity.ok(ApiResponse.success("获取成功", ids));
  }

  /** 获取未分配该角色的用户列表（简化为返回空数组，实际场景需根据用户表筛选） */
  @GetMapping("/role/{roleId}/unallocated-users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<Long>>> unallocatedUsers(@PathVariable("roleId") Long roleId) {
    return ResponseEntity.ok(ApiResponse.success("获取成功", List.of()));
  }

  /** 批量授权用户 */
  @PostMapping("/role/users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> grantUsers(@RequestBody Map<String, Object> body) {
    Long roleId = Long.valueOf(body.get("roleId").toString());
    List<Long> userIds = ((List<?>) body.get("userIds")).stream().map(Object::toString).map(Long::valueOf).toList();
    // 简化：逐个追加（生产建议做合并而非覆盖）
    for (Long uid : userIds) {
      List<Long> current = userRoleMapper.selectRoleIdsByUserId(uid);
      Set<Long> merged = new HashSet<>(current);
      merged.add(roleId);
      userRoleMapper.replaceUserRoles(uid, new ArrayList<>(merged));
    }
    return ResponseEntity.ok(ApiResponse.success("授权成功", null));
  }

  /** 批量取消授权 */
  @DeleteMapping("/role/users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> revokeUsers(@RequestBody Map<String, Object> body) {
    Long roleId = Long.valueOf(body.get("roleId").toString());
    List<Long> userIds = ((List<?>) body.get("userIds")).stream().map(Object::toString).map(Long::valueOf).toList();
    for (Long uid : userIds) {
      List<Long> current = userRoleMapper.selectRoleIdsByUserId(uid);
      current = current.stream().filter(id -> !id.equals(roleId)).toList();
      userRoleMapper.replaceUserRoles(uid, current);
    }
    return ResponseEntity.ok(ApiResponse.success("取消授权成功", null));
  }

  /** 查询角色持有的菜单ID */
  @GetMapping("/menu/role/{roleId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<Long>>> roleMenus(@PathVariable("roleId") Long roleId) {
    var mids = menuMapper.selectRoleMenuIds(roleId);
    return ResponseEntity.ok(ApiResponse.success("获取成功", mids));
  }

  /** 给角色分配菜单权限 */
  @PutMapping("/role/auth")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> roleAuth(@RequestBody Map<String, Object> body) {
    Long roleId = Long.valueOf(body.get("roleId").toString());
    List<Long> menuIds = ((List<?>) body.get("menuIds")).stream().map(Object::toString).map(Long::valueOf).toList();
    menuMapper.replaceRoleMenus(roleId, menuIds);
    return ResponseEntity.ok(ApiResponse.success("授权成功", null));
  }

  /** 给用户分配角色 */
  @PutMapping("/user/auth")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> userAuth(@RequestBody Map<String, Object> body) {
    Long userId = Long.valueOf(body.get("userId").toString());
    List<Long> roleIds = ((List<?>) body.get("roleIds")).stream().map(Object::toString).map(Long::valueOf).toList();
    userRoleMapper.replaceUserRoles(userId, roleIds);
    return ResponseEntity.ok(ApiResponse.success("授权成功", null));
  }
}
