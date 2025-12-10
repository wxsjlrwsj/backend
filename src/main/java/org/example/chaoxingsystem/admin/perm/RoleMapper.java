package org.example.chaoxingsystem.admin.perm;

import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface RoleMapper {
  int insert(Role r);
  int updateById(Role r);
  int deleteById(@Param("id") Long id);
  Role selectById(@Param("id") Long id);
  List<Role> selectAll();
  List<Role> selectPage(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);
  long count(@Param("keyword") String keyword);
}
