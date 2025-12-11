package org.example.chaoxingsystem.admin.org;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrgService {
  private final OrganizationMapper mapper;

  public OrgService(OrganizationMapper mapper) {
    this.mapper = mapper;
  }

  /** 获取完整组织树 */
  public List<Map<String, Object>> getTree() {
    List<Organization> all = mapper.selectAll();
    Map<Long, List<Organization>> byParent = all.stream().collect(Collectors.groupingBy(o -> o.getParentId() == null ? 0L : o.getParentId()));
    return buildChildren(byParent, 0L);
  }

  private List<Map<String, Object>> buildChildren(Map<Long, List<Organization>> byParent, Long parentId) {
    List<Organization> children = byParent.getOrDefault(parentId, List.of());
    List<Map<String, Object>> result = new ArrayList<>();
    for (Organization o : children) {
      Map<String, Object> node = new LinkedHashMap<>();
      node.put("id", o.getId());
      node.put("name", o.getName());
      node.put("code", o.getCode());
      node.put("type", o.getType());
      node.put("parentId", o.getParentId());
      node.put("children", buildChildren(byParent, o.getId()));
      result.add(node);
    }
    return result;
  }

  /** 创建或更新组织 */
  @Transactional
  public void save(Organization org, boolean isUpdate) {
    if (!isUpdate && mapper.countByCode(org.getCode()) > 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "机构编码已存在");
    }
    if (org.getParentId() != null) {
      Organization parent = mapper.selectById(org.getParentId());
      if (parent == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "父节点不存在");
      org.setPath(normalizePath(parent.getPath(), parent.getId()));
    } else {
      org.setPath("/");
    }
    if (org.getSortOrder() == null || org.getSortOrder() < 1) org.setSortOrder(1);
    if (isUpdate) mapper.updateById(org); else mapper.insert(org);
    // 更新自身及子树路径
    rebuildPath(org.getId());
  }

  /** 删除前做级联检查 */
  @Transactional
  public void delete(Long id, long studentRefCount, long teacherRefCount) {
    Organization org = mapper.selectById(id);
    if (org == null) return;
    if (mapper.countChildren(id) > 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "存在子机构，禁止删除");
    }
    if (studentRefCount > 0 || teacherRefCount > 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "存在关联人员，禁止删除");
    }
    mapper.deleteById(id);
  }

  /** 拖拽移动节点 */
  @Transactional
  public void move(Long draggingId, Long dropId, String dropType) {
    Organization drag = mapper.selectById(draggingId);
    Organization drop = mapper.selectById(dropId);
    if (drag == null || drop == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "节点不存在");
    // 防环：目标不能在拖拽节点的子树中
    String dragPathPrefix = normalizePath(drag.getPath(), drag.getId());
    if (drop.getPath() != null && drop.getPath().startsWith(dragPathPrefix)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "非法移动：不能移动到自身子孙");
    }
    if ("inner".equalsIgnoreCase(dropType)) {
      drag.setParentId(drop.getId());
      drag.setSortOrder(nextSortOrder(drop.getId()));
    } else {
      Long newParent = drop.getParentId();
      drag.setParentId(newParent);
      int base = Objects.requireNonNullElse(drop.getSortOrder(), 1);
      drag.setSortOrder("before".equalsIgnoreCase(dropType) ? base : base + 1);
      normalizeSiblingsOrder(newParent, drag.getId(), drag.getSortOrder());
    }
    mapper.updateById(drag);
    rebuildPath(drag.getId());
  }

  private int nextSortOrder(Long parentId) {
    List<Organization> siblings = mapper.selectChildren(parentId);
    return siblings.isEmpty() ? 1 : siblings.get(siblings.size() - 1).getSortOrder() + 1;
  }

  /** 将同级排序重新归一化为 1..N，插入拖拽节点的期望位置 */
  private void normalizeSiblingsOrder(Long parentId, Long insertId, int insertOrder) {
    List<Organization> siblings = mapper.selectChildren(parentId);
    int idx = 1;
    for (Organization s : siblings) {
      if (idx == insertOrder) {
        idx++;
      }
      if (!s.getId().equals(insertId)) {
        s.setSortOrder(idx++);
        mapper.updateById(s);
      }
    }
  }

  /** 重建某节点及其子树路径 */
  private void rebuildPath(Long id) {
    Organization node = mapper.selectById(id);
    String prefix = normalizePath(node.getParentId() == null ? "/" : mapper.selectById(node.getParentId()).getPath(), node.getParentId());
    node.setPath(normalizePath(prefix, node.getId()));
    mapper.updateById(node);
    List<Organization> children = mapper.selectSubtree(node.getPath());
    for (Organization child : children) {
      if (!child.getId().equals(node.getId())) {
        String parentPath = mapper.selectById(child.getParentId()).getPath();
        child.setPath(normalizePath(parentPath, child.getId()));
        mapper.updateById(child);
      }
    }
  }

  private String normalizePath(String parentPath, Long id) {
    String base = (parentPath == null || parentPath.isEmpty()) ? "/" : parentPath;
    if (!base.endsWith("/")) base = base + "/";
    return base + (id == null ? "" : id) + "/";
  }

  /** 获取机构成员列表：
   *  - class: 返回班级学生
   *  - department/dept: 返回部门教师
   *  - school/college/其他: 聚合子树中的班级学生与部门教师
   */
  public List<Map<String, Object>> getMembers(Long orgId) {
    Organization org = mapper.selectById(orgId);
    if (org == null) return List.of();
    String type = org.getType() == null ? "" : org.getType().toLowerCase();
    if ("class".equals(type)) {
      return mapper.listStudentMembersByClassIds(List.of(orgId));
    }
    if ("department".equals(type) || "dept".equals(type)) {
      return mapper.listTeacherMembersByDeptIds(List.of(orgId));
    }
    List<Organization> subtree = mapper.selectSubtree(org.getPath());
    List<Long> classIds = new ArrayList<>();
    List<Long> deptIds = new ArrayList<>();
    for (Organization o : subtree) {
      String t = o.getType() == null ? "" : o.getType().toLowerCase();
      if ("class".equals(t)) classIds.add(o.getId());
      if ("department".equals(t) || "dept".equals(t)) deptIds.add(o.getId());
    }
    List<Map<String, Object>> result = new ArrayList<>();
    if (!classIds.isEmpty()) result.addAll(mapper.listStudentMembersByClassIds(classIds));
    if (!deptIds.isEmpty()) result.addAll(mapper.listTeacherMembersByDeptIds(deptIds));
    return result;
  }
}
