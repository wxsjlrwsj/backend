package org.example.chaoxingsystem.admin.org.dto;

/** 创建/更新组织请求体 */
public class SaveOrgRequest {
  private Long id;
  private String name;
  private String code;
  private Long parentId;
  private String type; // school|college|department|class
  private Integer sortOrder;
  private String leader;
  private String phone;
  private String status; // 1|0
  private String description;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }
  public Long getParentId() { return parentId; }
  public void setParentId(Long parentId) { this.parentId = parentId; }
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public Integer getSortOrder() { return sortOrder; }
  public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
  public String getLeader() { return leader; }
  public void setLeader(String leader) { this.leader = leader; }
  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
}
