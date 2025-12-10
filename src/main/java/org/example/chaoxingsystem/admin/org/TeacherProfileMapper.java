package org.example.chaoxingsystem.admin.org;

import org.apache.ibatis.annotations.Param;

public interface TeacherProfileMapper {
  long countByDeptId(@Param("deptId") Long deptId);
}
