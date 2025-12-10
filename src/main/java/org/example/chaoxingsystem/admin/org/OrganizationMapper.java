package org.example.chaoxingsystem.admin.org;

import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface OrganizationMapper {
  int insert(Organization org);
  int updateById(Organization org);
  int deleteById(@Param("id") Long id);
  Organization selectById(@Param("id") Long id);
  Long countChildren(@Param("id") Long id);
  List<Organization> selectChildren(@Param("parentId") Long parentId);
  Long countByCode(@Param("code") String code);
  List<Organization> selectAll();
  List<Organization> selectSubtree(@Param("pathPrefix") String pathPrefix);
}
