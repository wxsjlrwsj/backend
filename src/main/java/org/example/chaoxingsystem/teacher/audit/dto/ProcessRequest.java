package org.example.chaoxingsystem.teacher.audit.dto;

import java.util.List;

public class ProcessRequest {
  private List<Long> ids;
  private Integer status;
  private String comment;

  public List<Long> getIds() { return ids; }
  public void setIds(List<Long> ids) { this.ids = ids; }
  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }
  public String getComment() { return comment; }
  public void setComment(String comment) { this.comment = comment; }
}
