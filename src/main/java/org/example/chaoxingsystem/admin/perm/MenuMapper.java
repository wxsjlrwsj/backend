package org.example.chaoxingsystem.admin.perm;

import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface MenuMapper {
  int insert(Menu m);
  int updateById(Menu m);
  int deleteById(@Param("id") Long id);
  Menu selectById(@Param("id") Long id);
  List<Menu> selectAll();
  List<Menu> selectByRoleId(@Param("roleId") Long roleId);
  List<Long> selectRoleMenuIds(@Param("roleId") Long roleId);
  int replaceRoleMenus(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
}
