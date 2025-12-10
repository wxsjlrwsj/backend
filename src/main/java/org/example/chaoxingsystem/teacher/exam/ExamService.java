package org.example.chaoxingsystem.teacher.exam;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** 考试服务：分页、创建/发布、看板数据 */
@Service
public class ExamService {
  private final ExamMapper mapper;

  public ExamService(ExamMapper mapper) { this.mapper = mapper; }

  public long count(Integer status) { return mapper.count(status); }

  public List<Exam> page(Integer status, int page, int size) {
    int offset = (Math.max(page, 1) - 1) * Math.max(size, 1);
    return mapper.selectPage(status, offset, size);
  }

  @Transactional
  public Long create(Long creatorId, String name, Long paperId, String startTime, Integer duration) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime st = LocalDateTime.parse(startTime, fmt);
    LocalDateTime et = st.plusMinutes(duration != null ? duration : 60);
    Exam e = new Exam();
    e.setName(name);
    e.setPaperId(paperId);
    e.setStartTime(startTime);
    e.setEndTime(et.format(fmt));
    e.setDuration(duration != null ? duration : 60);
    e.setStatus(0);
    e.setCreatorId(creatorId);
    mapper.insert(e);
    return e.getId();
  }
}

