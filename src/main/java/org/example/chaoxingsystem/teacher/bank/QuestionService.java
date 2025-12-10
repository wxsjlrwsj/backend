package org.example.chaoxingsystem.teacher.bank;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/** 题库服务：分页查询、创建/更新/删除、答案结构校验与归一化 */
@Service
public class QuestionService {
  private final QuestionMapper questionMapper;
  private final QuestionTypeMapper typeMapper;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public QuestionService(QuestionMapper questionMapper, QuestionTypeMapper typeMapper) {
    this.questionMapper = questionMapper;
    this.typeMapper = typeMapper;
  }

  public long count(String typeCode, String keyword, Integer difficulty, String subject) {
    return questionMapper.count(typeCode, keyword, difficulty, subject);
  }

  public List<Question> page(String typeCode, String keyword, Integer difficulty, String subject, int page, int size) {
    int offset = (Math.max(page, 1) - 1) * Math.max(size, 1);
    return questionMapper.selectPage(typeCode, keyword, difficulty, subject, offset, size);
  }

  @Transactional
  public Long create(Long creatorId, String typeCode, String content, String optionsJson, String answerJson, Integer difficulty, String subject, String knowledgePoints, String fileId) {
    QuestionType type = typeMapper.selectByCode(typeCode);
    if (type == null || type.getIsActive() == null || type.getIsActive() == 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "题型无效或未启用");
    }
    validateAnswer(type, answerJson, fileId);
    Question q = new Question();
    q.setTypeId(type.getTypeId());
    q.setContent(content);
    q.setOptions(optionsJson);
    q.setAnswer(answerJson);
    q.setDifficulty(difficulty);
    q.setSubject(subject);
    q.setKnowledgePoints(knowledgePoints);
    q.setFileId(fileId);
    q.setCreatorId(creatorId);
    questionMapper.insert(q);
    return q.getId();
  }

  @Transactional
  public void update(UpdateCommand cmd) {
    Question exist = questionMapper.selectById(cmd.id);
    if (exist == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "题目不存在");
    Integer typeId = exist.getTypeId();
    if (cmd.typeCode != null && !cmd.typeCode.isEmpty()) {
      QuestionType type = typeMapper.selectByCode(cmd.typeCode);
      if (type == null || type.getIsActive() == null || type.getIsActive() == 0) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "题型无效或未启用");
      }
      typeId = type.getTypeId();
    }
    if (cmd.answerJson != null) {
      QuestionType type = typeMapper.selectById(typeId);
      validateAnswer(type, cmd.answerJson, cmd.fileId);
    }
    Question q = new Question();
    q.setId(cmd.id);
    q.setTypeId(typeId);
    q.setContent(cmd.content != null ? cmd.content : exist.getContent());
    q.setOptions(cmd.optionsJson != null ? cmd.optionsJson : exist.getOptions());
    q.setAnswer(cmd.answerJson != null ? cmd.answerJson : exist.getAnswer());
    q.setAnalysis(cmd.analysis != null ? cmd.analysis : exist.getAnalysis());
    q.setDifficulty(cmd.difficulty != null ? cmd.difficulty : exist.getDifficulty());
    q.setSubject(cmd.subject != null ? cmd.subject : exist.getSubject());
    q.setKnowledgePoints(cmd.knowledgePoints != null ? cmd.knowledgePoints : exist.getKnowledgePoints());
    if (cmd.useFile != null && !cmd.useFile) {
      q.setFileId(null);
    } else {
      q.setFileId(cmd.fileId != null ? cmd.fileId : exist.getFileId());
    }
    q.setStatus(cmd.status != null ? cmd.status : exist.getStatus());
    questionMapper.updateById(q);
  }

  @Transactional
  public void delete(Long id) {
    questionMapper.deleteById(id);
  }

  private void validateAnswer(QuestionType type, String answerJson, String fileId) {
    if (answerJson == null || answerJson.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "答案不能为空");
    }
    try {
      // 简化校验：仅验证答案 JSON 可解析；fileId 全可选（包括编程题）
      objectMapper.readTree(answerJson);
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "答案结构不合法");
    }
  }

  /** 更新命令对象 */
  public static class UpdateCommand {
    public Long id;
    public String typeCode;
    public String content;
    public String optionsJson;
    public String answerJson;
    public String analysis;
    public Integer difficulty;
    public String subject;
    public String knowledgePoints;
    public String fileId;
    public Integer status;
    public Boolean useFile;
  }
}
