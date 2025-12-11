package org.example.chaoxingsystem.student.exam;

import org.example.chaoxingsystem.teacher.exam.Exam;
import org.example.chaoxingsystem.teacher.exam.ExamService;
import org.example.chaoxingsystem.user.UserService;
import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student/exams")
public class StudentExamController {
  private final ExamService examService;
  private final StudentExamService studentExamService;
  private final UserService userService;

  public StudentExamController(ExamService examService, StudentExamService studentExamService, UserService userService) {
    this.examService = examService;
    this.studentExamService = studentExamService;
    this.userService = userService;
  }

  @GetMapping
  @PreAuthorize("hasRole('STUDENT')")
  public ResponseEntity<ApiResponse<HashMap<String, Object>>> list(
    @RequestParam(value = "status", required = false) String status,
    @RequestParam(value = "page", defaultValue = "1") int page,
    @RequestParam(value = "size", defaultValue = "10") int size
  ) {
    Integer st = null;
    if ("upcoming".equalsIgnoreCase(status)) st = 0;
    if ("ongoing".equalsIgnoreCase(status)) st = 1;
    if ("finished".equalsIgnoreCase(status)) st = 2;
    long total = examService.count(st);
    List<Exam> list = examService.page(st, page, size);
    HashMap<String, Object> data = new HashMap<>();
    data.put("list", list);
    data.put("total", total);
    return ResponseEntity.ok(ApiResponse.success("获取成功", data));
  }

  @GetMapping("/{examId}/paper")
  @PreAuthorize("hasRole('STUDENT')")
  public ResponseEntity<ApiResponse<Map<String, Object>>> paper(@PathVariable("examId") Long examId) {
    Map<String, Object> data = studentExamService.getPaper(examId);
    return ResponseEntity.ok(ApiResponse.success("获取成功", data));
  }

  @PostMapping("/{examId}/submit")
  @PreAuthorize("hasRole('STUDENT')")
  public ResponseEntity<ApiResponse<Void>> submit(Authentication auth, @PathVariable("examId") Long examId, @RequestBody Map<String, Object> body) {
    var me = userService.getByUsername(auth.getName());
    Map<String, Object> answers = (Map<String, Object>) body.get("answers");
    Integer durationUsed = body.get("durationUsed") instanceof Number ? ((Number) body.get("durationUsed")).intValue() : null;
    studentExamService.submit(examId, me.getId(), answers, durationUsed);
    return ResponseEntity.ok(ApiResponse.success("提交成功", null));
  }
}
