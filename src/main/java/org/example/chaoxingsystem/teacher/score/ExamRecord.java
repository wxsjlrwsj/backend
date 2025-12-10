package org.example.chaoxingsystem.teacher.score;

import java.time.Instant;

/** 考试记录实体 */
public class ExamRecord {
  private Long id;
  private Long examId;
  private Long studentId;
  private Integer score;
  private Integer status;
  private String startTime;
  private String submitTime;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getExamId() { return examId; }
  public void setExamId(Long examId) { this.examId = examId; }
  public Long getStudentId() { return studentId; }
  public void setStudentId(Long studentId) { this.studentId = studentId; }
  public Integer getScore() { return score; }
  public void setScore(Integer score) { this.score = score; }
  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }
  public String getStartTime() { return startTime; }
  public void setStartTime(String startTime) { this.startTime = startTime; }
  public String getSubmitTime() { return submitTime; }
  public void setSubmitTime(String submitTime) { this.submitTime = submitTime; }
}

