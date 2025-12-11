package org.example.chaoxingsystem.admin.perm;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface UserRoleMapper {
  List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);
  List<String> selectRoleKeysByUserId(@Param("userId") Long userId);
  int replaceUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
  List<Long> selectUserIdsByRoleId(@Param("roleId") Long roleId);

  List<Map<String, Object>> selectAllocatedUsersByRole(@Param("roleId") Long roleId,
                                                       @Param("username") String username,
                                                       @Param("realName") String realName,
                                                       @Param("offset") int offset,
                                                       @Param("limit") int limit);
  Long countAllocatedUsersByRole(@Param("roleId") Long roleId,
                                 @Param("username") String username,
                                 @Param("realName") String realName);

  List<Map<String, Object>> selectUnallocatedUsersByRole(@Param("roleId") Long roleId,
                                                         @Param("username") String username,
                                                         @Param("realName") String realName,
                                                         @Param("orgId") Long orgId,
                                                         @Param("offset") int offset,
                                                         @Param("limit") int limit);
  Long countUnallocatedUsersByRole(@Param("roleId") Long roleId,
                                   @Param("username") String username,
                                   @Param("realName") String realName,
                                   @Param("orgId") Long orgId);

  List<Map<String, Object>> selectUsersByUserType(@Param("userType") String userType,
                                                  @Param("username") String username,
                                                  @Param("realName") String realName,
                                                  @Param("offset") int offset,
                                                  @Param("limit") int limit);
  Long countUsersByUserType(@Param("userType") String userType,
                            @Param("username") String username,
                            @Param("realName") String realName);
}
