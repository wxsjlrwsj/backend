package org.example.chaoxingsystem.student.exam;

import org.apache.ibatis.annotations.Param;

public interface StudentExamMapper {
  int insertRecord(@Param("examId") Long examId, @Param("studentId") Long studentId);
  int markSubmitted(@Param("recordId") Long recordId);
  int deleteAnswersByRecordId(@Param("recordId") Long recordId);
  int insertAnswer(@Param("recordId") Long recordId, @Param("questionId") Long questionId, @Param("studentAnswer") String studentAnswer);
}
