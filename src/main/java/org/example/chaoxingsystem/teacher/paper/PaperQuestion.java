package org.example.chaoxingsystem.teacher.paper;

/** 试卷题目关联实体 */
public class PaperQuestion {
  private Long paperId;
  private Long questionId;
  private Integer score;
  private Integer sortOrder;

  public Long getPaperId() { return paperId; }
  public void setPaperId(Long paperId) { this.paperId = paperId; }
  public Long getQuestionId() { return questionId; }
  public void setQuestionId(Long questionId) { this.questionId = questionId; }
  public Integer getScore() { return score; }
  public void setScore(Integer score) { this.score = score; }
  public Integer getSortOrder() { return sortOrder; }
  public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}

