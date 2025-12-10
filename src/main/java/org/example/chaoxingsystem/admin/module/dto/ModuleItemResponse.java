package org.example.chaoxingsystem.admin.module.dto;

import java.time.Instant;
import java.util.List;

public class ModuleItemResponse {
  private Long id;
  private String name;
  private String code;
  private String category;
  private String version;
  private Boolean enabled;
  private Boolean showInMenu;
  private String routePath;
  private List<String> allowedRoles;
  private List<String> dependencies;
  private String description;
  private Instant updatedAt;

  public ModuleItemResponse(Long id, String name, String code, String category, String version, Boolean enabled, Boolean showInMenu, String routePath, List<String> allowedRoles, List<String> dependencies, String description, Instant updatedAt) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.category = category;
    this.version = version;
    this.enabled = enabled;
    this.showInMenu = showInMenu;
    this.routePath = routePath;
    this.allowedRoles = allowedRoles;
    this.dependencies = dependencies;
    this.description = description;
    this.updatedAt = updatedAt;
  }

  public Long getId() { return id; }
  public String getName() { return name; }
  public String getCode() { return code; }
  public String getCategory() { return category; }
  public String getVersion() { return version; }
  public Boolean getEnabled() { return enabled; }
  public Boolean getShowInMenu() { return showInMenu; }
  public String getRoutePath() { return routePath; }
  public List<String> getAllowedRoles() { return allowedRoles; }
  public List<String> getDependencies() { return dependencies; }
  public String getDescription() { return description; }
  public Instant getUpdatedAt() { return updatedAt; }
}
