package org.example.chaoxingsystem.user;

import org.example.chaoxingsystem.user.dto.RegisterRequest;
import org.example.chaoxingsystem.user.dto.ResetPasswordRequest;
import org.springframework.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;
import java.util.stream.Collectors;
import org.example.chaoxingsystem.user.dto.UserInfo;
import org.example.chaoxingsystem.user.dto.UserResponse;
import java.time.ZoneId;

@Service
public class UserService {
  private final UserMapper userMapper;
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public UserService(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Transactional
  public User register(RegisterRequest request) {
    if (userMapper.countByUsername(request.getUsername()) > 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "用户名已存在");
    }
    if (userMapper.countByEmail(request.getEmail()) > 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "邮箱已存在");
    }

    User user = new User();
    user.setUsername(request.getUsername().trim());
    user.setEmail(request.getEmail().trim().toLowerCase());
    String hash = passwordEncoder.encode(request.getPassword());
    user.setPasswordHash(hash);
    String userType = request.getUserType().trim().toLowerCase();
    user.setUserType(userType);
    String realName = request.getUsername().trim();
    user.setRealName(realName);
    user.setAvatar(null);

    userMapper.insert(user);
    return user;
  }

  public User authenticate(String username, String rawPassword) {
    User u = userMapper.selectByUsername(username);
    if (u != null && passwordEncoder.matches(rawPassword, u.getPasswordHash())) {
      return u;
    }
    return null;
  }

  @Transactional
  public boolean resetPassword(ResetPasswordRequest request) {
    if (!StringUtils.hasText(request.getUsername()) || !StringUtils.hasText(request.getEmail())) {
      return false;
    }
    User u = userMapper.selectByUsername(request.getUsername());
    if (u == null) {
      return false;
    }
    if (!request.getEmail().equalsIgnoreCase(u.getEmail())) {
      return false;
    }
    String hash = passwordEncoder.encode(request.getNewPassword());
    int updated = userMapper.updatePasswordById(u.getId(), hash);
    return updated > 0;
  }

  public UserInfo getUserInfoByUsername(String username) {
    User u = userMapper.selectByUsername(username);
    if (u == null) { return null; }
    return new UserInfo(u.getId(), u.getUsername(), u.getRealName(), u.getUserType(), u.getAvatar());
  }

  public List<UserResponse> listAllUsers() {
    return userMapper.selectAll().stream()
      .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getEmail(),
        u.getCreatedAt() != null ? u.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant() : null))
      .collect(Collectors.toList());
  }
}
