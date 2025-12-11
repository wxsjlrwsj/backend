package org.example.chaoxingsystem.admin.module;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统模块服务：分页统计、CRUD、启用/禁用切换、字符串与列表的互转工具
 */
@Service
public class SystemModuleService {
  private final SystemModuleMapper mapper;

  public SystemModuleService(SystemModuleMapper mapper) {
    this.mapper = mapper;
  }

  public long count(String keyword, String category, Boolean enabled) {
    return mapper.count(keyword, category, enabled);
  }

  public List<SystemModule> page(String keyword, String category, Boolean enabled, int page, int size) {
    int p = Math.max(page, 1);
    int s = Math.max(size, 1);
    int offset = (p - 1) * s;
    return mapper.selectPage(keyword, category, enabled, offset, s);
  }

  public List<SystemModule> visibleByRole(String role) {
    return mapper.selectVisibleByRole(role);
  }

  @Transactional
  public Long create(SystemModule m) {
    if (mapper.countByCode(m.getCode()) > 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "模块编码已存在");
    }
    mapper.insert(m);
    return m.getId();
  }

  @Transactional
  public void update(Long id, SystemModule m) {
    SystemModule exist = mapper.selectById(id);
    if (exist == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "模块不存在");
    }
    m.setId(id);
    mapper.updateById(m);
  }

  @Transactional
  public void delete(Long id) {
    mapper.deleteById(id);
  }

  @Transactional
  public void switchStatus(Long id, boolean enabled) {
    SystemModule exist = mapper.selectById(id);
    if (exist == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "模块不存在");
    }
    exist.setEnabled(enabled);
    mapper.updateById(exist);
  }

  public static String join(List<String> list) {
    return list == null ? "" : list.stream().map(String::trim).collect(Collectors.joining(","));
  }

  public static List<String> split(String s) {
    if (s == null || s.isEmpty()) return List.of();
    return Arrays.stream(s.split(",")).map(String::trim).filter(x -> !x.isEmpty()).collect(Collectors.toList());
  }
}
