package org.example.chaoxingsystem.teacher.score;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/** 成绩 Mapper */
public interface ScoreMapper {
  long count(@Param("examId") Long examId, @Param("classId") Long classId, @Param("keyword") String keyword);
  List<Map<String, Object>> selectPage(@Param("examId") Long examId, @Param("classId") Long classId, @Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);
  ExamRecord selectRecord(@Param("examId") Long examId, @Param("studentId") Long studentId);
  List<ExamAnswer> selectAnswersByRecordId(@Param("recordId") Long recordId);
  int updateRecordScore(@Param("recordId") Long recordId, @Param("score") Integer score, @Param("status") Integer status);
  int updateAnswerScore(@Param("recordId") Long recordId, @Param("questionId") Long questionId, @Param("score") Integer score, @Param("comment") String comment);
  Map<String, Object> selectStats(@Param("examId") Long examId);
}
