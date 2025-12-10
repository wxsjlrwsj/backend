package org.example.chaoxingsystem.admin.org;

/** 组织机构实体，对应表 sys_organization */
public class Organization {
  private Long id;
  private Long parentId;
  private String name;
  private String code;
  private String type;
  private Integer sortOrder;
  private String path;
  private Integer status;
  private String leader;
  private String phone;
  private String description;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getParentId() { return parentId; }
  public void setParentId(Long parentId) { this.parentId = parentId; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public Integer getSortOrder() { return sortOrder; }
  public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
  public String getPath() { return path; }
  public void setPath(String path) { this.path = path; }
  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }
  public String getLeader() { return leader; }
  public void setLeader(String leader) { this.leader = leader; }
  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
}
