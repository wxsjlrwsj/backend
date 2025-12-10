package org.example.chaoxingsystem.teacher.bank;

import org.apache.ibatis.annotations.Param;
import java.util.List;

/** 题型字典查询 Mapper */
public interface QuestionTypeMapper {
  QuestionType selectByCode(@Param("typeCode") String typeCode);
  QuestionType selectById(@Param("typeId") Integer typeId);
  List<QuestionType> selectActive();
}

