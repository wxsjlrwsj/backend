package org.example.chaoxingsystem.teacher.bank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/** 创建题目请求 */
public class CreateQuestionRequest {
  @NotBlank
  private String typeCode;
  @NotBlank
  private String content;
  private List<OptionItem> options;
  @NotNull
  private Object answer;
  @NotNull
  private Integer difficulty;
  @NotBlank
  private String subject;
  private String knowledgePoints;
  private String fileId;
  private Boolean useFile;

  public String getTypeCode() { return typeCode; }
  public void setTypeCode(String typeCode) { this.typeCode = typeCode; }
  public String getContent() { return content; }
  public void setContent(String content) { this.content = content; }
  public List<OptionItem> getOptions() { return options; }
  public void setOptions(List<OptionItem> options) { this.options = options; }
  public Object getAnswer() { return answer; }
  public void setAnswer(Object answer) { this.answer = answer; }
  public Integer getDifficulty() { return difficulty; }
  public void setDifficulty(Integer difficulty) { this.difficulty = difficulty; }
  public String getSubject() { return subject; }
  public void setSubject(String subject) { this.subject = subject; }
  public String getKnowledgePoints() { return knowledgePoints; }
  public void setKnowledgePoints(String knowledgePoints) { this.knowledgePoints = knowledgePoints; }
  public String getFileId() { return fileId; }
  public void setFileId(String fileId) { this.fileId = fileId; }
  public Boolean getUseFile() { return useFile; }
  public void setUseFile(Boolean useFile) { this.useFile = useFile; }
}
