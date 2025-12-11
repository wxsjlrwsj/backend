package org.example.chaoxingsystem.admin.org;

import org.apache.ibatis.annotations.Param;

public interface StudentProfileMapper {
  long countByClassId(@Param("classId") Long classId);
  Long countByUserId(@Param("userId") Long userId);
  int updateClassIdByUserId(@Param("userId") Long userId, @Param("classId") Long classId);
  int insertStudentProfile(@Param("userId") Long userId,
                           @Param("studentNo") String studentNo,
                           @Param("realName") String realName,
                           @Param("gender") Integer gender,
                           @Param("classId") Long classId,
                           @Param("majorCode") String majorCode,
                           @Param("enrollmentYear") Integer enrollmentYear,
                           @Param("politicsStatus") String politicsStatus);
}
