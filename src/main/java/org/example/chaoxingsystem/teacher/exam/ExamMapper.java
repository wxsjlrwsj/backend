package org.example.chaoxingsystem.teacher.exam;

import org.apache.ibatis.annotations.Param;
import java.util.List;

/** 考试 Mapper */
public interface ExamMapper {
  long count(@Param("status") Integer status);
  List<Exam> selectPage(@Param("status") Integer status, @Param("offset") int offset, @Param("limit") int limit);
  int insert(Exam e);
  int updateById(Exam e);
  Exam selectById(@Param("id") Long id);
}

