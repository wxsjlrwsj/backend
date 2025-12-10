package org.example.chaoxingsystem.admin.module;

import java.time.Instant;

public class SystemModule {
  private Long id;
  private String name;
  private String code;
  private String category;
  private String version;
  private Boolean enabled;
  private Boolean showInMenu;
  private String routePath;
  private String allowedRoles;
  private String dependencies;
  private String description;
  private Instant updatedAt;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }
  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }
  public String getVersion() { return version; }
  public void setVersion(String version) { this.version = version; }
  public Boolean getEnabled() { return enabled; }
  public void setEnabled(Boolean enabled) { this.enabled = enabled; }
  public Boolean getShowInMenu() { return showInMenu; }
  public void setShowInMenu(Boolean showInMenu) { this.showInMenu = showInMenu; }
  public String getRoutePath() { return routePath; }
  public void setRoutePath(String routePath) { this.routePath = routePath; }
  public String getAllowedRoles() { return allowedRoles; }
  public void setAllowedRoles(String allowedRoles) { this.allowedRoles = allowedRoles; }
  public String getDependencies() { return dependencies; }
  public void setDependencies(String dependencies) { this.dependencies = dependencies; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public Instant getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
