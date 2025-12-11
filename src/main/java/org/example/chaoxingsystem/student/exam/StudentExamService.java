package org.example.chaoxingsystem.student.exam;

import org.example.chaoxingsystem.teacher.exam.ExamMapper;
import org.example.chaoxingsystem.teacher.exam.Exam;
import org.example.chaoxingsystem.teacher.paper.PaperMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StudentExamService {
  private final ExamMapper examMapper;
  private final PaperMapper paperMapper;
  private final StudentExamMapper studentExamMapper;
  private final org.example.chaoxingsystem.teacher.score.ScoreMapper scoreMapper;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public StudentExamService(ExamMapper examMapper, PaperMapper paperMapper, StudentExamMapper studentExamMapper, org.example.chaoxingsystem.teacher.score.ScoreMapper scoreMapper) {
    this.examMapper = examMapper;
    this.paperMapper = paperMapper;
    this.studentExamMapper = studentExamMapper;
    this.scoreMapper = scoreMapper;
  }

  public Map<String, Object> getPaper(Long examId) {
    Exam e = examMapper.selectById(examId);
    if (e == null) return Map.of();
    var p = paperMapper.selectById(e.getPaperId());
    List<Map<String, Object>> qs = paperMapper.selectPaperQuestionViews(e.getPaperId());
    Map<String, Object> data = new HashMap<>();
    data.put("paperId", p != null ? p.getId() : null);
    data.put("name", p != null ? p.getName() : null);
    data.put("subject", p != null ? p.getSubject() : null);
    data.put("questions", qs);
    return data;
  }

  @Transactional
  public void submit(Long examId, Long studentId, Map<String, Object> answers, Integer durationUsed) {
    var record = scoreMapper.selectRecord(examId, studentId);
    Long recordId;
    if (record == null) {
      studentExamMapper.insertRecord(examId, studentId);
      record = scoreMapper.selectRecord(examId, studentId);
    }
    recordId = record.getId();
    studentExamMapper.deleteAnswersByRecordId(recordId);
    for (Map.Entry<String, Object> e : answers.entrySet()) {
      Long qid = Long.valueOf(e.getKey());
      String ansJson = null;
      try {
        ansJson = e.getValue() != null ? objectMapper.writeValueAsString(e.getValue()) : null;
      } catch (Exception ex) {
        ansJson = null;
      }
      studentExamMapper.insertAnswer(recordId, qid, ansJson);
    }
    studentExamMapper.markSubmitted(recordId);
  }
}
