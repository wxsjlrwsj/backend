package org.example.chaoxingsystem.teacher.audit;

import java.time.Instant;

public class QuestionAudit {
  private Long id;
  private Long submitterId;
  private Instant submitTime;
  private Integer typeId;
  private String content;
  private String answer;
  private Integer difficulty;
  private Long subjectId;
  private Integer status;
  private String auditComment;
  private Long auditorId;
  private Instant auditTime;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getSubmitterId() { return submitterId; }
  public void setSubmitterId(Long submitterId) { this.submitterId = submitterId; }
  public Instant getSubmitTime() { return submitTime; }
  public void setSubmitTime(Instant submitTime) { this.submitTime = submitTime; }
  public Integer getTypeId() { return typeId; }
  public void setTypeId(Integer typeId) { this.typeId = typeId; }
  public String getContent() { return content; }
  public void setContent(String content) { this.content = content; }
  public String getAnswer() { return answer; }
  public void setAnswer(String answer) { this.answer = answer; }
  public Integer getDifficulty() { return difficulty; }
  public void setDifficulty(Integer difficulty) { this.difficulty = difficulty; }
  public Long getSubjectId() { return subjectId; }
  public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }
  public String getAuditComment() { return auditComment; }
  public void setAuditComment(String auditComment) { this.auditComment = auditComment; }
  public Long getAuditorId() { return auditorId; }
  public void setAuditorId(Long auditorId) { this.auditorId = auditorId; }
  public Instant getAuditTime() { return auditTime; }
  public void setAuditTime(Instant auditTime) { this.auditTime = auditTime; }
}
