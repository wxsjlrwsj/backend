package org.example.chaoxingsystem.teacher.paper;

import org.apache.ibatis.annotations.Param;
import java.util.List;

/** 试卷 Mapper */
public interface PaperMapper {
  long count(@Param("subject") String subject);
  List<Paper> selectPage(@Param("subject") String subject, @Param("offset") int offset, @Param("limit") int limit);
  int insert(Paper p);
  int updateById(Paper p);
  Paper selectById(@Param("id") Long id);
  int deleteById(@Param("id") Long id);

  int deletePaperQuestions(@Param("paperId") Long paperId);
  int insertPaperQuestions(@Param("paperId") Long paperId, @Param("items") List<PaperQuestion> items);
  List<java.util.Map<String, Object>> selectPaperQuestionViews(@Param("paperId") Long paperId);
}
