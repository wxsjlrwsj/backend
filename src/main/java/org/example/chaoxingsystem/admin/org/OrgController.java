package org.example.chaoxingsystem.admin.org;

import jakarta.validation.Valid;
import org.example.chaoxingsystem.admin.org.dto.MoveRequest;
import org.example.chaoxingsystem.admin.org.dto.SaveOrgRequest;
import org.example.chaoxingsystem.config.ModuleCheck;
import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.example.chaoxingsystem.user.User;
import org.example.chaoxingsystem.user.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/** 组织机构管理接口 */
@RestController
@RequestMapping("/api/org")
@ModuleCheck(moduleCode = "sys_org")
public class OrgController {
  private final OrgService service;
  private final OrganizationMapper mapper;
  private final StudentProfileMapper studentMapper;
  private final TeacherProfileMapper teacherMapper;
  private final UserMapper userMapper;

  public OrgController(OrgService service, OrganizationMapper mapper, StudentProfileMapper studentMapper, TeacherProfileMapper teacherMapper, UserMapper userMapper) {
    this.service = service;
    this.mapper = mapper;
    this.studentMapper = studentMapper;
    this.teacherMapper = teacherMapper;
    this.userMapper = userMapper;
  }

  /** 获取组织机构树 */
  @GetMapping("/tree")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<?>>> tree() {
    var data = service.getTree();
    return ResponseEntity.ok(ApiResponse.success("success", data));
  }

  /** 拖拽移动/排序机构 */
  @PostMapping("/move")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> move(@Valid @RequestBody MoveRequest req) {
    service.move(req.getDraggingNodeId(), req.getDropNodeId(), req.getDropType());
    return ResponseEntity.ok(ApiResponse.success("移动成功", null));
  }

  /** 创建/更新机构 */
  @PostMapping("/save")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> save(@Valid @RequestBody SaveOrgRequest req) {
    Organization org = new Organization();
    org.setId(req.getId());
    org.setName(req.getName());
    org.setCode(req.getCode());
    org.setParentId(req.getParentId());
    org.setType(req.getType());
    org.setSortOrder(req.getSortOrder());
    org.setLeader(req.getLeader());
    org.setPhone(req.getPhone());
    org.setStatus("1".equals(req.getStatus()) ? 1 : 0);
    org.setDescription(req.getDescription());
    service.save(org, req.getId() != null);
    return ResponseEntity.ok(ApiResponse.success("保存成功", null));
  }

  /** 删除机构（含关联检查） */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
    // 统计关联人员：班级关联学生、部门关联教师
    Organization org = mapper.selectById(id);
    long studentRef = 0;
    long teacherRef = 0;
    if (org != null) {
      if ("class".equalsIgnoreCase(org.getType())) studentRef = studentMapper.countByClassId(id);
      if ("department".equalsIgnoreCase(org.getType()) || "dept".equalsIgnoreCase(org.getType())) teacherRef = teacherMapper.countByDeptId(id);
    }
    service.delete(id, studentRef, teacherRef);
    return ResponseEntity.ok(ApiResponse.success("删除成功", null));
  }

  /** 获取某机构下的成员列表（按类型聚合：班级->学生，部门->教师；上级节点聚合子树） */
  @GetMapping("/{id}/members")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<Map<String, Object>>>> members(@PathVariable("id") Long id) {
    try {
      var list = service.getMembers(id);
      return ResponseEntity.ok(ApiResponse.success("success", list));
    } catch (Exception ex) {
      return ResponseEntity.ok(ApiResponse.success("success", List.of()));
    }
  }

  /** 为机构分配成员（班级->学生，部门->教师）。支持按用户名分配，自动创建档案或更新归属 */
  @PostMapping("/{id}/members/assign")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> assignMember(@PathVariable("id") Long id, @RequestBody Map<String, Object> req) {
    Organization org = mapper.selectById(id);
    if (org == null) {
      return ResponseEntity.badRequest().body(ApiResponse.error(400, "机构不存在"));
    }
    String type = org.getType() == null ? "" : org.getType().toLowerCase();
    if (!"class".equals(type) && !"department".equals(type) && !"dept".equals(type)) {
      return ResponseEntity.badRequest().body(ApiResponse.error(400, "仅支持班级或部门类型分配成员"));
    }
    Object userIdObj = req.get("userId");
    Object usernameObj = req.get("username");
    User user = null;
    if (userIdObj instanceof Number) {
      Long uid = ((Number) userIdObj).longValue();
      user = userMapper.selectById(uid);
    } else if (usernameObj instanceof String) {
      user = userMapper.selectByUsername(((String) usernameObj).trim());
    }
    if (user == null) {
      return ResponseEntity.badRequest().body(ApiResponse.error(400, "用户不存在"));
    }
    String userType = user.getUserType() == null ? "" : user.getUserType().toLowerCase();
    if ("class".equals(type) && !"student".equals(userType)) {
      return ResponseEntity.badRequest().body(ApiResponse.error(400, "仅能为班级分配学生"));
    }
    if (("department".equals(type) || "dept".equals(type)) && !"teacher".equals(userType)) {
      return ResponseEntity.badRequest().body(ApiResponse.error(400, "仅能为部门分配教师"));
    }
    int year = java.time.LocalDate.now().getYear();
    if ("student".equals(userType)) {
      Long exist = studentMapper.countByUserId(user.getId());
      if (exist != null && exist > 0) {
        studentMapper.updateClassIdByUserId(user.getId(), id);
      } else {
        String sno = "S" + year + String.format("%04d", user.getId());
        String major = "UNASSIGNED";
        studentMapper.insertStudentProfile(user.getId(), sno, user.getRealName(), null, id, major, year, null);
      }
    } else if ("teacher".equals(userType)) {
      Long exist = teacherMapper.countByUserId(user.getId());
      if (exist != null && exist > 0) {
        teacherMapper.updateDeptIdByUserId(user.getId(), id);
      } else {
        String tno = "T" + year + String.format("%04d", user.getId());
        teacherMapper.insertTeacherProfile(user.getId(), tno, user.getRealName(), id, null, null);
      }
    }
    return ResponseEntity.ok(ApiResponse.success("分配成功", null));
  }

  /** 候选成员搜索（根据机构类型：班级->student，部门->teacher） */
  @GetMapping("/{id}/members/candidates")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Map<String, Object>>> candidates(@PathVariable("id") Long id,
                                                                     @RequestParam(value = "keyword", required = false) String keyword,
                                                                     @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    Organization org = mapper.selectById(id);
    if (org == null) {
      return ResponseEntity.badRequest().body(ApiResponse.error(400, "机构不存在"));
    }
    String type = org.getType() == null ? "" : org.getType().toLowerCase();
    String userType;
    if ("class".equals(type)) userType = "student"; else if ("department".equals(type) || "dept".equals(type)) userType = "teacher"; else userType = "";
    if (userType.isEmpty()) {
      Map<String, Object> data = new HashMap<>();
      data.put("list", List.of());
      data.put("total", 0L);
      return ResponseEntity.ok(ApiResponse.success("获取成功", data));
    }
    int offset = (Math.max(pageNum, 1) - 1) * Math.max(pageSize, 1);
    var list = userMapper.searchByUserType(userType, keyword, offset, pageSize);
    Long total = userMapper.countByUserTypeAndKeyword(userType, keyword);
    Map<String, Object> data = new HashMap<>();
    data.put("list", list);
    data.put("total", total);
    return ResponseEntity.ok(ApiResponse.success("获取成功", data));
  }
}
