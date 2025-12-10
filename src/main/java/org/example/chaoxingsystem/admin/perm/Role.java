package org.example.chaoxingsystem.admin.perm;

/** RBAC 角色实体 */
public class Role {
  private Long id;
  private String roleName;
  private String roleKey; // admin|teacher|student
  private String dataScope; // 1..5
  private Integer status;
  private String remark;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getRoleName() { return roleName; }
  public void setRoleName(String roleName) { this.roleName = roleName; }
  public String getRoleKey() { return roleKey; }
  public void setRoleKey(String roleKey) { this.roleKey = roleKey; }
  public String getDataScope() { return dataScope; }
  public void setDataScope(String dataScope) { this.dataScope = dataScope; }
  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }
  public String getRemark() { return remark; }
  public void setRemark(String remark) { this.remark = remark; }
}
