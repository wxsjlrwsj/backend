package org.example.chaoxingsystem.user.dto;

public class LoginData {
  private String token;
  private UserInfo userInfo;

  public LoginData(String token, UserInfo userInfo) {
    this.token = token;
    this.userInfo = userInfo;
  }

  public String getToken() {
    return token;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }
}