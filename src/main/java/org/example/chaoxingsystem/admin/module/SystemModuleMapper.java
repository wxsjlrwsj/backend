package org.example.chaoxingsystem.admin.module;

import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SystemModuleMapper {
  int insert(SystemModule m);
  int updateById(SystemModule m);
  int deleteById(@Param("id") Long id);
  SystemModule selectById(@Param("id") Long id);
  Long countByCode(@Param("code") String code);
  SystemModule selectByCode(@Param("code") String code);
  Long count(@Param("keyword") String keyword, @Param("category") String category, @Param("enabled") Boolean enabled);
  List<SystemModule> selectPage(@Param("keyword") String keyword, @Param("category") String category, @Param("enabled") Boolean enabled, @Param("offset") int offset, @Param("limit") int limit);
  List<SystemModule> selectVisibleByRole(@Param("role") String role);
}
