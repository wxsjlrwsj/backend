package org.example.chaoxingsystem.teacher.paper;

import java.time.Instant;

/** 试卷实体 */
public class Paper {
  private Long id;
  private String name;
  private String subject;
  private Integer totalScore;
  private Integer passScore;
  private Integer questionCount;
  private Integer status;
  private Long creatorId;
  private Instant createTime;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getSubject() { return subject; }
  public void setSubject(String subject) { this.subject = subject; }
  public Integer getTotalScore() { return totalScore; }
  public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }
  public Integer getPassScore() { return passScore; }
  public void setPassScore(Integer passScore) { this.passScore = passScore; }
  public Integer getQuestionCount() { return questionCount; }
  public void setQuestionCount(Integer questionCount) { this.questionCount = questionCount; }
  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }
  public Long getCreatorId() { return creatorId; }
  public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
  public Instant getCreateTime() { return createTime; }
  public void setCreateTime(Instant createTime) { this.createTime = createTime; }
}

