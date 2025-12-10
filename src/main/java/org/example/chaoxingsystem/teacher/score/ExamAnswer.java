package org.example.chaoxingsystem.teacher.score;

/** 答题明细实体 */
public class ExamAnswer {
  private Long id;
  private Long recordId;
  private Long questionId;
  private String studentAnswer;
  private String fileId;
  private Integer score;
  private Boolean isCorrect;
  private String comment;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getRecordId() { return recordId; }
  public void setRecordId(Long recordId) { this.recordId = recordId; }
  public Long getQuestionId() { return questionId; }
  public void setQuestionId(Long questionId) { this.questionId = questionId; }
  public String getStudentAnswer() { return studentAnswer; }
  public void setStudentAnswer(String studentAnswer) { this.studentAnswer = studentAnswer; }
  public String getFileId() { return fileId; }
  public void setFileId(String fileId) { this.fileId = fileId; }
  public Integer getScore() { return score; }
  public void setScore(Integer score) { this.score = score; }
  public Boolean getIsCorrect() { return isCorrect; }
  public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }
  public String getComment() { return comment; }
  public void setComment(String comment) { this.comment = comment; }
}

