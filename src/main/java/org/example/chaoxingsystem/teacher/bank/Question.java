package org.example.chaoxingsystem.teacher.bank;

import java.time.Instant;

/** 题库题目实体 */
public class Question {
  private Long id;
  private Integer typeId;
  private String content;
  private String options;
  private String answer;
  private String analysis;
  private Integer difficulty;
  private String subject;
  private String knowledgePoints;
  private String fileId;
  private Long creatorId;
  private Instant createTime;
  private Integer status;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Integer getTypeId() { return typeId; }
  public void setTypeId(Integer typeId) { this.typeId = typeId; }
  public String getContent() { return content; }
  public void setContent(String content) { this.content = content; }
  public String getOptions() { return options; }
  public void setOptions(String options) { this.options = options; }
  public String getAnswer() { return answer; }
  public void setAnswer(String answer) { this.answer = answer; }
  public String getAnalysis() { return analysis; }
  public void setAnalysis(String analysis) { this.analysis = analysis; }
  public Integer getDifficulty() { return difficulty; }
  public void setDifficulty(Integer difficulty) { this.difficulty = difficulty; }
  public String getSubject() { return subject; }
  public void setSubject(String subject) { this.subject = subject; }
  public String getKnowledgePoints() { return knowledgePoints; }
  public void setKnowledgePoints(String knowledgePoints) { this.knowledgePoints = knowledgePoints; }
  public String getFileId() { return fileId; }
  public void setFileId(String fileId) { this.fileId = fileId; }
  public Long getCreatorId() { return creatorId; }
  public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
  public Instant getCreateTime() { return createTime; }
  public void setCreateTime(Instant createTime) { this.createTime = createTime; }
  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }
}

