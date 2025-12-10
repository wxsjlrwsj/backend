package org.example.chaoxingsystem.teacher.audit;

import org.example.chaoxingsystem.config.ModuleCheck;
import org.example.chaoxingsystem.teacher.audit.dto.ProcessRequest;
import org.example.chaoxingsystem.user.UserService;
import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 教师题目审核接口：
 * - 列表分页与筛选
 * - 批量处理（通过/驳回）
 * - 单条详情
 */
@RestController
@RequestMapping("/api/audit/question")
@ModuleCheck(moduleCode = "tch_audit")
public class AuditController {
  private final QuestionAuditService service;
  private final UserService userService;

  public AuditController(QuestionAuditService service, UserService userService) {
    this.service = service;
    this.userService = userService;
  }

  @GetMapping("/list")
  @PreAuthorize("hasRole('TEACHER')")
  public ResponseEntity<ApiResponse<HashMap<String, Object>>> list(
    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
    @RequestParam(value = "status", required = false) Integer status,
    @RequestParam(value = "submitterName", required = false) String submitterName,
    @RequestParam(value = "beginTime", required = false) String beginTime,
    @RequestParam(value = "endTime", required = false) String endTime
  ) {
    long total = service.count(status, submitterName, beginTime, endTime);
    List<QuestionAuditListRow> list = service.page(status, submitterName, beginTime, endTime, pageNum, pageSize);
    HashMap<String, Object> data = new HashMap<>();
    data.put("list", list);
    data.put("total", total);
    return ResponseEntity.ok(ApiResponse.success("获取成功", data));
  }

  @PutMapping("/process")
  @PreAuthorize("hasRole('TEACHER')")
  public ResponseEntity<ApiResponse<HashMap<String, Object>>> process(Authentication auth, @RequestBody ProcessRequest req) {
    var me = userService.getByUsername(auth.getName());
    int affected = service.process(req.getIds(), req.getStatus(), req.getComment(), me.getId());
    HashMap<String, Object> data = new HashMap<>();
    data.put("affected", affected);
    return ResponseEntity.ok(ApiResponse.success("处理成功", data));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('TEACHER')")
  public ResponseEntity<ApiResponse<QuestionAuditDetail>> detail(@PathVariable("id") Long id) {
    var d = service.detail(id);
    return ResponseEntity.ok(ApiResponse.success("获取成功", d));
  }
}
