package org.example.chaoxingsystem.teacher.audit;

import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface QuestionAuditMapper {
  long count(
    @Param("status") Integer status,
    @Param("submitterName") String submitterName,
    @Param("beginTime") String beginTime,
    @Param("endTime") String endTime
  );

  List<QuestionAuditListRow> selectPage(
    @Param("status") Integer status,
    @Param("submitterName") String submitterName,
    @Param("beginTime") String beginTime,
    @Param("endTime") String endTime,
    @Param("offset") int offset,
    @Param("limit") int limit
  );

  QuestionAuditDetail selectDetail(@Param("id") Long id);

  int processBatch(@Param("ids") List<Long> ids, @Param("status") Integer status, @Param("comment") String comment, @Param("auditorId") Long auditorId);
}
