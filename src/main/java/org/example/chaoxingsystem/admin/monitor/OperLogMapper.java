package org.example.chaoxingsystem.admin.monitor;

import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface OperLogMapper {
  int insert(OperLog log);
  int deleteByIds(@Param("ids") List<Long> ids);
  int clean();
  long count(
    @Param("title") String title,
    @Param("operName") String operName,
    @Param("businessType") Integer businessType,
    @Param("status") Integer status,
    @Param("beginTime") String beginTime,
    @Param("endTime") String endTime
  );
  List<OperLog> selectPage(
    @Param("title") String title,
    @Param("operName") String operName,
    @Param("businessType") Integer businessType,
    @Param("status") Integer status,
    @Param("beginTime") String beginTime,
    @Param("endTime") String endTime,
    @Param("offset") int offset,
    @Param("limit") int limit
  );
}
