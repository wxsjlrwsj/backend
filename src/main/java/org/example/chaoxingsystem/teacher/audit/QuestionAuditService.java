package org.example.chaoxingsystem.teacher.audit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 题目审核领域服务：列表统计/分页、详情查询、批量处理
 */
@Service
public class QuestionAuditService {
  private final QuestionAuditMapper mapper;

  public QuestionAuditService(QuestionAuditMapper mapper) {
    this.mapper = mapper;
  }

  public long count(Integer status, String submitterName, String beginTime, String endTime) {
    return mapper.count(status, submitterName, beginTime, endTime);
  }

  public List<QuestionAuditListRow> page(Integer status, String submitterName, String beginTime, String endTime, int pageNum, int pageSize) {
    int offset = (Math.max(pageNum, 1) - 1) * Math.max(pageSize, 1);
    return mapper.selectPage(status, submitterName, beginTime, endTime, offset, pageSize);
  }

  public QuestionAuditDetail detail(Long id) {
    return mapper.selectDetail(id);
  }

  @Transactional
  public int process(List<Long> ids, Integer status, String comment, Long auditorId) {
    if (ids == null || ids.isEmpty()) return 0;
    if (status == null || (status != 1 && status != 2)) return 0;
    return mapper.processBatch(ids, status, comment, auditorId);
  }
}
