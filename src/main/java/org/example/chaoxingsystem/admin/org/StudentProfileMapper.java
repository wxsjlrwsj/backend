package org.example.chaoxingsystem.admin.org;

import org.apache.ibatis.annotations.Param;

public interface StudentProfileMapper {
  long countByClassId(@Param("classId") Long classId);
}
