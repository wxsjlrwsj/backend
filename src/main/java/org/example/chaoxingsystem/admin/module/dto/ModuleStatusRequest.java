package org.example.chaoxingsystem.admin.module.dto;

import jakarta.validation.constraints.NotNull;

public class ModuleStatusRequest {
  @NotNull
  private Boolean enabled;
  public Boolean getEnabled() { return enabled; }
  public void setEnabled(Boolean enabled) { this.enabled = enabled; }
}
