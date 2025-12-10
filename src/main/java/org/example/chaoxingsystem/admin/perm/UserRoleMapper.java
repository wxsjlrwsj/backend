package org.example.chaoxingsystem.admin.perm;

import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserRoleMapper {
  List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);
  int replaceUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
  List<Long> selectUserIdsByRoleId(@Param("roleId") Long roleId);
}
