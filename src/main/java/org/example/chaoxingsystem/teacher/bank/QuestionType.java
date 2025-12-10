package org.example.chaoxingsystem.teacher.bank;

/** 题型字典实体 */
public class QuestionType {
  private Integer typeId;
  private String typeName;
  private String typeCode;
  private String answerJson;
  private String configJson;
  private Integer isActive;

  public Integer getTypeId() { return typeId; }
  public void setTypeId(Integer typeId) { this.typeId = typeId; }
  public String getTypeName() { return typeName; }
  public void setTypeName(String typeName) { this.typeName = typeName; }
  public String getTypeCode() { return typeCode; }
  public void setTypeCode(String typeCode) { this.typeCode = typeCode; }
  public String getAnswerJson() { return answerJson; }
  public void setAnswerJson(String answerJson) { this.answerJson = answerJson; }
  public String getConfigJson() { return configJson; }
  public void setConfigJson(String configJson) { this.configJson = configJson; }
  public Integer getIsActive() { return isActive; }
  public void setIsActive(Integer isActive) { this.isActive = isActive; }
}

