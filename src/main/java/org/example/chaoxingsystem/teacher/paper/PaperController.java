package org.example.chaoxingsystem.teacher.paper;

import jakarta.validation.Valid;
import org.example.chaoxingsystem.config.ModuleCheck;
import org.example.chaoxingsystem.teacher.paper.dto.CreatePaperRequest;
import org.example.chaoxingsystem.teacher.paper.dto.QuestionScore;
import org.example.chaoxingsystem.user.UserService;
import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 试卷管理接口：列表、创建、智能组卷 */
@RestController
@RequestMapping("/api")
@ModuleCheck(moduleCode = "tch_paper")
public class PaperController {
  private final PaperService service;
  private final UserService userService;

  public PaperController(PaperService service, UserService userService) {
    this.service = service;
    this.userService = userService;
  }

  @GetMapping("/papers")
  @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
  public ResponseEntity<ApiResponse<HashMap<String, Object>>> list(
    @RequestParam(value = "page", defaultValue = "1") int page,
    @RequestParam(value = "size", defaultValue = "10") int size,
    @RequestParam(value = "subject", required = false) String subject
  ) {
    long total = service.count(subject);
    List<Paper> list = service.page(subject, page, size);
    HashMap<String, Object> data = new HashMap<>();
    data.put("list", list);
    data.put("total", total);
    return ResponseEntity.ok(ApiResponse.success("获取成功", data));
  }

  @PostMapping("/papers")
  @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
  public ResponseEntity<ApiResponse<HashMap<String, Object>>> create(Authentication auth, @Valid @RequestBody CreatePaperRequest req) {
    var me = userService.getByUsername(auth.getName());
    List<PaperService.QuestionItem> items = req.getQuestions().stream().map(q -> new PaperService.QuestionItem(q.getId(), q.getScore())).toList();
    Long id = service.create(me.getId(), req.getName(), req.getSubject(), items, req.getPassScore());
    HashMap<String, Object> data = new HashMap<>();
    data.put("id", id);
    return ResponseEntity.ok(ApiResponse.success("创建成功", data));
  }

  @PostMapping("/papers/auto-generate")
  @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
  public ResponseEntity<ApiResponse<HashMap<String, Object>>> autoGenerate(Authentication auth, @RequestBody Map<String, Object> body) {
    var me = userService.getByUsername(auth.getName());
    String subject = (String) body.get("subject");
    Integer difficulty = body.get("difficulty") instanceof Number ? ((Number) body.get("difficulty")).intValue() : null;
    Integer totalScore = body.get("totalScore") instanceof Number ? ((Number) body.get("totalScore")).intValue() : null;
    Map<String, Integer> typeDistribution = (Map<String, Integer>) body.get("typeDistribution");
    Long id = service.autoGenerate(me.getId(), subject, difficulty, totalScore, typeDistribution);
    HashMap<String, Object> data = new HashMap<>();
    data.put("id", id);
    return ResponseEntity.ok(ApiResponse.success("智能组卷成功", data));
  }
}

