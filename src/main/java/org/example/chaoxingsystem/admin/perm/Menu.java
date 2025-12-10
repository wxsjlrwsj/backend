package org.example.chaoxingsystem.admin.perm;

/** 菜单/权限资源实体 */
public class Menu {
  private Long id;
  private Long parentId;
  private String name;
  private String type; // M|C|F
  private String perms;
  private String path;
  private String component;
  private String icon;
  private Integer sortOrder;
  private Integer visible;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getParentId() { return parentId; }
  public void setParentId(Long parentId) { this.parentId = parentId; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public String getPerms() { return perms; }
  public void setPerms(String perms) { this.perms = perms; }
  public String getPath() { return path; }
  public void setPath(String path) { this.path = path; }
  public String getComponent() { return component; }
  public void setComponent(String component) { this.component = component; }
  public String getIcon() { return icon; }
  public void setIcon(String icon) { this.icon = icon; }
  public Integer getSortOrder() { return sortOrder; }
  public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
  public Integer getVisible() { return visible; }
  public void setVisible(Integer visible) { this.visible = visible; }
}
