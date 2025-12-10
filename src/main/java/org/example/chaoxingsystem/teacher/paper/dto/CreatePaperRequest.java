package org.example.chaoxingsystem.teacher.paper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/** 创建试卷请求 */
public class CreatePaperRequest {
  @NotBlank
  private String name;
  @NotBlank
  private String subject;
  @NotNull
  private List<QuestionScore> questions;
  private Integer passScore;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getSubject() { return subject; }
  public void setSubject(String subject) { this.subject = subject; }
  public List<QuestionScore> getQuestions() { return questions; }
  public void setQuestions(List<QuestionScore> questions) { this.questions = questions; }
  public Integer getPassScore() { return passScore; }
  public void setPassScore(Integer passScore) { this.passScore = passScore; }
}

