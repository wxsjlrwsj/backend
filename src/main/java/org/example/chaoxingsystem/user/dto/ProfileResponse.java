package org.example.chaoxingsystem.user.dto;

public class ProfileResponse {
  private String username;
  private String realName;
  private String role;
  private String phone;
  private String email;
  private String avatarUrl;
  private String studentNo;
  private String className;
  private String teacherNo;
  private String deptName;

  public ProfileResponse(String username, String realName, String role, String phone, String email, String avatarUrl) {
    this.username = username;
    this.realName = realName;
    this.role = role;
    this.phone = phone;
    this.email = email;
    this.avatarUrl = avatarUrl;
  }

  public String getUsername() { return username; }
  public String getRealName() { return realName; }
  public String getRole() { return role; }
  public String getPhone() { return phone; }
  public String getEmail() { return email; }
  public String getAvatarUrl() { return avatarUrl; }
  public String getStudentNo() { return studentNo; }
  public String getClassName() { return className; }
  public String getTeacherNo() { return teacherNo; }
  public String getDeptName() { return deptName; }
  public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
  public void setClassName(String className) { this.className = className; }
  public void setTeacherNo(String teacherNo) { this.teacherNo = teacherNo; }
  public void setDeptName(String deptName) { this.deptName = deptName; }
}
