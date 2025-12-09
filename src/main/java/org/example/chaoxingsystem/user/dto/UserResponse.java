package org.example.chaoxingsystem.user.dto;

import java.time.Instant;

public class UserResponse {
  private Long id;
  private String username;
  private String email;
  private Instant createdAt;

  public UserResponse(Long id, String username, String email, Instant createdAt) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}