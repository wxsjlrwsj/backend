package org.example.chaoxingsystem.teacher.exam;

import jakarta.validation.Valid;
import org.example.chaoxingsystem.config.ModuleCheck;
import org.example.chaoxingsystem.user.UserService;
import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 考试管理接口：列表、创建/发布、监考看板 */
@RestController
@RequestMapping("/api")
@ModuleCheck(moduleCode = "tch_exam")
public class ExamController {
  private final ExamService service;
  private final UserService userService;

  public ExamController(ExamService service, UserService userService) {
    this.service = service;
    this.userService = userService;
  }

  @GetMapping("/exams")
  @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
  public ResponseEntity<ApiResponse<HashMap<String, Object>>> list(
    @RequestParam(value = "status", required = false) String status,
    @RequestParam(value = "page", defaultValue = "1") int page,
    @RequestParam(value = "size", defaultValue = "10") int size
  ) {
    Integer st = null;
    if ("upcoming".equalsIgnoreCase(status)) st = 0;
    if ("ongoing".equalsIgnoreCase(status)) st = 1;
    if ("finished".equalsIgnoreCase(status)) st = 2;
    long total = service.count(st);
    List<Exam> list = service.page(st, page, size);
    HashMap<String, Object> data = new HashMap<>();
    data.put("list", list);
    data.put("total", total);
    return ResponseEntity.ok(ApiResponse.success("获取成功", data));
  }

  @PostMapping("/exams")
  @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
  public ResponseEntity<ApiResponse<HashMap<String, Object>>> create(Authentication auth, @RequestBody Map<String, Object> body) {
    var me = userService.getByUsername(auth.getName());
    String name = (String) body.get("name");
    Long paperId = body.get("paperId") instanceof Number ? ((Number) body.get("paperId")).longValue() : null;
    String startTime = (String) body.get("startTime");
    Integer duration = body.get("duration") instanceof Number ? ((Number) body.get("duration")).intValue() : null;
    Long id = service.create(me.getId(), name, paperId, startTime, duration);
    HashMap<String, Object> data = new HashMap<>();
    data.put("id", id);
    return ResponseEntity.ok(ApiResponse.success("创建成功", data));
  }

  @GetMapping("/monitor/{examId}")
  @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
  public ResponseEntity<ApiResponse<Map<String, Object>>> monitor(@PathVariable("examId") Long examId) {
    // 简化：返回静态看板数据占位
    Map<String, Object> data = new HashMap<>();
    data.put("examId", examId);
    data.put("online", 0);
    data.put("submitted", 0);
    data.put("cheatSuspected", 0);
    return ResponseEntity.ok(ApiResponse.success("获取成功", data));
  }
}

