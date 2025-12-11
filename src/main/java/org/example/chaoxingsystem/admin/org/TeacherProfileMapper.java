package org.example.chaoxingsystem.admin.org;

import org.apache.ibatis.annotations.Param;

public interface TeacherProfileMapper {
  long countByDeptId(@Param("deptId") Long deptId);
  Long countByUserId(@Param("userId") Long userId);
  int updateDeptIdByUserId(@Param("userId") Long userId, @Param("deptId") Long deptId);
  int insertTeacherProfile(@Param("userId") Long userId,
                           @Param("teacherNo") String teacherNo,
                           @Param("realName") String realName,
                           @Param("deptId") Long deptId,
                           @Param("title") String title,
                           @Param("entryDate") String entryDate);
}
