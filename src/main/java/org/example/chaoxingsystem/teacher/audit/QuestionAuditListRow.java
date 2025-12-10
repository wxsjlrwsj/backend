package org.example.chaoxingsystem.teacher.audit;

import java.time.Instant;

public class QuestionAuditListRow {
  private Long id;
  private Long submitterId;
  private String submitterName;
  private Instant submitTime;
  private Integer typeId;
  private String typeCode;
  private Integer difficulty;
  private Long subjectId;
  private Integer status;

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
  public Integer getDifficulty() { return difficulty; }
  public void setDifficulty(Integer difficulty) { this.difficulty = difficulty; }
  public Long getSubjectId() { return subjectId; }
  public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }
}
