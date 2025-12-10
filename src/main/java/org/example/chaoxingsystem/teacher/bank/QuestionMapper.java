package org.example.chaoxingsystem.teacher.bank;

import org.apache.ibatis.annotations.Param;
import java.util.List;

/** 题库 Mapper */
public interface QuestionMapper {
  long count(@Param("typeCode") String typeCode, @Param("keyword") String keyword, @Param("difficulty") Integer difficulty, @Param("subject") String subject);
  List<Question> selectPage(@Param("typeCode") String typeCode, @Param("keyword") String keyword, @Param("difficulty") Integer difficulty, @Param("subject") String subject, @Param("offset") int offset, @Param("limit") int limit);
  int insert(Question q);
  int updateById(Question q);
  int deleteById(@Param("id") Long id);
  Question selectById(@Param("id") Long id);
  List<Question> selectByTypeSubjectDifficultyLimit(@Param("typeCode") String typeCode, @Param("subject") String subject, @Param("difficulty") Integer difficulty, @Param("limit") int limit);
}
