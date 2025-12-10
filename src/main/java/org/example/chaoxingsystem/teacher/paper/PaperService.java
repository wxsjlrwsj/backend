package org.example.chaoxingsystem.teacher.paper;

import org.example.chaoxingsystem.teacher.bank.Question;
import org.example.chaoxingsystem.teacher.bank.QuestionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 试卷服务：分页、组卷、智能组卷 */
@Service
public class PaperService {
  private final PaperMapper paperMapper;
  private final QuestionMapper questionMapper;

  public PaperService(PaperMapper paperMapper, QuestionMapper questionMapper) {
    this.paperMapper = paperMapper;
    this.questionMapper = questionMapper;
  }

  public long count(String subject) {
    return paperMapper.count(subject);
  }

  public List<Paper> page(String subject, int page, int size) {
    int offset = (Math.max(page, 1) - 1) * Math.max(size, 1);
    return paperMapper.selectPage(subject, offset, size);
  }

  @Transactional
  public Long create(Long creatorId, String name, String subject, List<QuestionItem> items, Integer passScore) {
    if (items == null || items.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "试题不能为空");
    int totalScore = items.stream().mapToInt(i -> i.score).sum();
    Paper p = new Paper();
    p.setName(name);
    p.setSubject(subject);
    p.setTotalScore(totalScore);
    p.setPassScore(passScore != null ? passScore : Math.max(60, (int)Math.round(totalScore * 0.6)));
    p.setQuestionCount(items.size());
    p.setStatus(0);
    p.setCreatorId(creatorId);
    paperMapper.insert(p);
    List<PaperQuestion> rels = new ArrayList<>();
    int order = 1;
    for (QuestionItem i : items) {
      PaperQuestion r = new PaperQuestion();
      r.setQuestionId(i.id);
      r.setScore(i.score);
      r.setSortOrder(order++);
      rels.add(r);
    }
    paperMapper.deletePaperQuestions(p.getId());
    paperMapper.insertPaperQuestions(p.getId(), rels);
    return p.getId();
  }

  @Transactional
  public Long autoGenerate(Long creatorId, String subject, Integer difficulty, Integer totalScore, Map<String, Integer> typeDistribution) {
    if (subject == null || subject.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "科目必填");
    if (totalScore == null || totalScore <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "总分无效");
    List<QuestionItem> items = new ArrayList<>();
    int accumulated = 0;
    for (Map.Entry<String, Integer> e : typeDistribution.entrySet()) {
      String typeCode = e.getKey();
      int countNeed = e.getValue();
      List<Question> qs = questionMapper.selectByTypeSubjectDifficultyLimit(typeCode, subject, difficulty, countNeed);
      for (Question q : qs) {
        int scoreEach = Math.max(1, totalScore / Math.max(1, typeDistribution.values().stream().mapToInt(Integer::intValue).sum()));
        items.add(new QuestionItem(q.getId(), scoreEach));
        accumulated += scoreEach;
      }
    }
    if (items.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "题库不足，无法组卷");
    // 调整最后一题以满足总分
    if (accumulated != totalScore) {
      int diff = totalScore - accumulated;
      QuestionItem last = items.get(items.size() - 1);
      last.score += diff;
    }
    String name = subject + "智能组卷";
    return create(creatorId, name, subject, items, null);
  }

  /** 组卷题目项 */
  public static class QuestionItem {
    public Long id;
    public Integer score;
    public QuestionItem(Long id, Integer score) { this.id = id; this.score = score; }
  }
}

