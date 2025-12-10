package org.example.chaoxingsystem.admin.monitor;

import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/** 操作日志管理接口 */
@RestController
@RequestMapping("/api/monitor/operlog")
public class MonitorController {
  private final OperLogMapper mapper;
  public MonitorController(OperLogMapper mapper) { this.mapper = mapper; }

  @GetMapping("/list")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<OperLog>>> list(
    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
    @RequestParam(value = "title", required = false) String title,
    @RequestParam(value = "operName", required = false) String operName,
    @RequestParam(value = "businessType", required = false) Integer businessType,
    @RequestParam(value = "status", required = false) Integer status,
    @RequestParam(value = "beginTime", required = false) String beginTime,
    @RequestParam(value = "endTime", required = false) String endTime
  ) {
    int offset = (Math.max(pageNum, 1) - 1) * Math.max(pageSize, 1);
    var list = mapper.selectPage(title, operName, businessType, status, beginTime, endTime, offset, pageSize);
    return ResponseEntity.ok(ApiResponse.success("获取成功", list));
  }

  @DeleteMapping("/{operIds}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("operIds") String operIds) {
    List<Long> ids = Arrays.stream(operIds.split(",")).map(String::trim).filter(s -> !s.isEmpty()).map(Long::valueOf).toList();
    mapper.deleteByIds(ids);
    return ResponseEntity.ok(ApiResponse.success("删除成功", null));
  }

  @DeleteMapping("/clean")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> clean() {
    mapper.clean();
    return ResponseEntity.ok(ApiResponse.success("清空成功", null));
  }
}
