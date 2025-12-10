package org.example.chaoxingsystem.teacher.exam;

import java.time.Instant;

/** 考试实体 */
public class Exam {
  private Long id;
  private String name;
  private Long paperId;
  private String startTime;
  private String endTime;
  private Integer duration;
  private Integer status;
  private Long creatorId;
  private Instant createTime;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Long getPaperId() { return paperId; }
  public void setPaperId(Long paperId) { this.paperId = paperId; }
  public String getStartTime() { return startTime; }
  public void setStartTime(String startTime) { this.startTime = startTime; }
  public String getEndTime() { return endTime; }
  public void setEndTime(String endTime) { this.endTime = endTime; }
  public Integer getDuration() { return duration; }
  public void setDuration(Integer duration) { this.duration = duration; }
  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }
  public Long getCreatorId() { return creatorId; }
  public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
  public Instant getCreateTime() { return createTime; }
  public void setCreateTime(Instant createTime) { this.createTime = createTime; }
}

