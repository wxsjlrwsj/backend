package org.example.chaoxingsystem.teacher.audit;

import java.time.Instant;

public class QuestionAuditDetail {
  private Long id;
  private Long submitterId;
  private String submitterName;
  private Instant submitTime;
  private Integer typeId;
  private String typeCode;
  private String content;
  private String answer;
  private Integer difficulty;
  private Long subjectId;
  private Integer status;
  private String auditComment;
  private Long auditorId;
  private String auditorName;
  private Instant auditTime;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getSubmitterId() { return submitterId; }
  public void setSubmitterId(Long submitterId) { this.submitterId = submitterId; }
  public String getSubmitterName() { return submitterName; }
  public void setSubmitterName(String submitterName) { this.submitterName = submitterName; }
  public Instant getSubmitTime() { return submitTime; }
  public void setSubmitTime(Instant submitTime) { this.submitTime = submitTime; }
  public Integer getTypeId() { return typeId; }
  public void setTypeId(Integer typeId) { this.typeId = typeId; }
  public String getTypeCode() { return typeCode; }
  public void setTypeCode(String typeCode) { this.typeCode = typeCode; }
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
  public String getAuditorName() { return auditorName; }
  public void setAuditorName(String auditorName) { this.auditorName = auditorName; }
  public Instant getAuditTime() { return auditTime; }
  public void setAuditTime(Instant auditTime) { this.auditTime = auditTime; }
}
