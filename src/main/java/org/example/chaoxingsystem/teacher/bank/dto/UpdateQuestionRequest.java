package org.example.chaoxingsystem.teacher.bank.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/** 更新题目请求 */
public class UpdateQuestionRequest {
  @NotNull
  private Long id;
  private String typeCode;
  private String content;
  private List<OptionItem> options;
  private Object answer;
  private String analysis;
  private Integer difficulty;
  private String subject;
  private String knowledgePoints;
  private String fileId;
  private Integer status;
  private Boolean useFile;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getTypeCode() { return typeCode; }
  public void setTypeCode(String typeCode) { this.typeCode = typeCode; }
  public String getContent() { return content; }
  public void setContent(String content) { this.content = content; }
  public List<OptionItem> getOptions() { return options; }
  public void setOptions(List<OptionItem> options) { this.options = options; }
  public Object getAnswer() { return answer; }
  public void setAnswer(Object answer) { this.answer = answer; }
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
  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }
  public Boolean getUseFile() { return useFile; }
  public void setUseFile(Boolean useFile) { this.useFile = useFile; }
}
