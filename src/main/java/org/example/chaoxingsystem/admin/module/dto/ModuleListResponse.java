package org.example.chaoxingsystem.admin.module.dto;

import java.util.List;

public class ModuleListResponse {
  private List<ModuleItemResponse> list;
  private long total;

  public ModuleListResponse(List<ModuleItemResponse> list, long total) {
    this.list = list;
    this.total = total;
  }

  public List<ModuleItemResponse> getList() { return list; }
  public long getTotal() { return total; }
}
