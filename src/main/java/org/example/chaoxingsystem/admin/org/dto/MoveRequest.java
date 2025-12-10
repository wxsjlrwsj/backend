package org.example.chaoxingsystem.admin.org.dto;

/** 组织拖拽移动请求 */
public class MoveRequest {
  private Long draggingNodeId;
  private Long dropNodeId;
  private String dropType; // inner|before|after

  public Long getDraggingNodeId() { return draggingNodeId; }
  public void setDraggingNodeId(Long draggingNodeId) { this.draggingNodeId = draggingNodeId; }
  public Long getDropNodeId() { return dropNodeId; }
  public void setDropNodeId(Long dropNodeId) { this.dropNodeId = dropNodeId; }
  public String getDropType() { return dropType; }
  public void setDropType(String dropType) { this.dropType = dropType; }
}
