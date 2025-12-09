package org.example.chaoxingsystem.user.dto;

public class UserInfo {
  private Long id;
  private String username;
  private String realName;
  private String userType;
  private String avatar;

  public UserInfo(Long id, String username, String realName, String userType, String avatar) {
    this.id = id;
    this.username = username;
    this.realName = realName;
    this.userType = userType;
    this.avatar = avatar;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getRealName() {
    return realName;
  }

  public String getUserType() {
    return userType;
  }

  public String getAvatar() {
    return avatar;
  }
}