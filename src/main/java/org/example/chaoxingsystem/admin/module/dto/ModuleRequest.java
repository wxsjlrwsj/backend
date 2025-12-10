package org.example.chaoxingsystem.admin.module.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ModuleRequest {
  @NotBlank
  private String name;
  @NotBlank
  private String code;
  @NotBlank
  private String category;
  @NotBlank
  private String version;
  @NotNull
  private Boolean enabled;
  @NotNull
  private Boolean showInMenu;
  @NotBlank
  private String routePath;
  @NotNull
  private List<String> allowedRoles;
  @NotNull
  private List<String> dependencies;
  private String description;

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
  public List<String> getAllowedRoles() { return allowedRoles; }
  public void setAllowedRoles(List<String> allowedRoles) { this.allowedRoles = allowedRoles; }
  public List<String> getDependencies() { return dependencies; }
  public void setDependencies(List<String> dependencies) { this.dependencies = dependencies; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
}
