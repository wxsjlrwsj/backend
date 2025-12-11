package org.example.chaoxingsystem.teacher.score;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 成绩服务：列表、详情与阅卷提交 */
@Service
public class ScoreService {
  private final ScoreMapper mapper;

  public ScoreService(ScoreMapper mapper) { this.mapper = mapper; }

  public long count(Long examId, Long classId, String keyword) { return mapper.count(examId, classId, keyword); }

  public List<Map<String, Object>> page(Long examId, Long classId, String keyword, int page, int size) {
    int offset = (Math.max(page, 1) - 1) * Math.max(size, 1);
    return mapper.selectPage(examId, classId, keyword, offset, size);
  }

  public Map<String, Object> detail(Long examId, Long studentId) {
    ExamRecord r = mapper.selectRecord(examId, studentId);
    if (r == null) return Map.of();
    List<ExamAnswer> answers = mapper.selectAnswersByRecordId(r.getId());
    Map<String, Object> data = new HashMap<>();
    data.put("record", r);
    data.put("answers", answers);
    return data;
  }

  @Transactional
  public void grade(Long examId, Long studentId, Integer totalScore, List<Map<String, Object>> questions) {
    ExamRecord r = mapper.selectRecord(examId, studentId);
    if (r == null) return;
    mapper.updateRecordScore(r.getId(), totalScore, 2);
    for (Map<String, Object> q : questions) {
      Long qid = q.get("id") instanceof Number ? ((Number) q.get("id")).longValue() : null;
      Integer score = q.get("givenScore") instanceof Number ? ((Number) q.get("givenScore")).intValue() : null;
      String comment = (String) q.get("comment");
      mapper.updateAnswerScore(r.getId(), qid, score, comment);
    }
  }

  public Map<String, Object> stats(Long examId) {
    Map<String, Object> m = mapper.selectStats(examId);
    if (m == null) return Map.of();
    Number total = (Number) m.get("total");
    Number passCount = (Number) m.get("passCount");
    double rate = 0.0;
    if (total != null && total.intValue() > 0 && passCount != null) {
      rate = ((double) passCount.intValue()) / ((double) total.intValue());
    }
    Map<String, Object> data = new HashMap<>(m);
    data.put("passRate", rate);
    return data;
  }
}
