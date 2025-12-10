package org.example.chaoxingsystem.admin.org;

import jakarta.validation.Valid;
import org.example.chaoxingsystem.admin.org.dto.MoveRequest;
import org.example.chaoxingsystem.admin.org.dto.SaveOrgRequest;
import org.example.chaoxingsystem.config.ModuleCheck;
import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

  public OrgController(OrgService service, OrganizationMapper mapper, StudentProfileMapper studentMapper, TeacherProfileMapper teacherMapper) {
    this.service = service;
    this.mapper = mapper;
    this.studentMapper = studentMapper;
    this.teacherMapper = teacherMapper;
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
      if ("department".equalsIgnoreCase(org.getType())) teacherRef = teacherMapper.countByDeptId(id);
    }
    service.delete(id, studentRef, teacherRef);
    return ResponseEntity.ok(ApiResponse.success("删除成功", null));
  }
}
