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

/**
 * 用户领域服务：注册、认证、资料维护、密码修改、用户列表等
 */
@Service
public class UserService {
  private final UserMapper userMapper;
  private final org.example.chaoxingsystem.admin.perm.UserRoleMapper userRoleMapper;
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public UserService(UserMapper userMapper, org.example.chaoxingsystem.admin.perm.UserRoleMapper userRoleMapper) {
    this.userMapper = userMapper;
    this.userRoleMapper = userRoleMapper;
  }

  @Transactional
  public User register(RegisterRequest request) {
    // 唯一性校验：用户名/邮箱不可重复
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
    // 基于用户名+邮箱双重匹配后重置密码
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
    String resolvedType = resolveUserType(u);
    return new UserInfo(u.getId(), u.getUsername(), u.getRealName(), resolvedType, u.getAvatar());
  }

  public User getByUsername(String username) {
    return userMapper.selectByUsername(username);
  }

  public List<UserResponse> listAllUsers() {
    return userMapper.selectAll().stream()
      .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getEmail(),
        u.getCreatedAt() != null ? u.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant() : null))
      .collect(Collectors.toList());
  }

  @Transactional
  public boolean updateProfile(String username, String email, String phone) {
    User u = userMapper.selectByUsername(username);
    if (u == null) { return false; }
    if (!u.getEmail().equalsIgnoreCase(email) && userMapper.countByEmail(email) > 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "邮箱已存在");
    }
    int updated = userMapper.updateProfileById(u.getId(), email.trim().toLowerCase(), phone != null ? phone.trim() : null);
    return updated > 0;
  }

  @Transactional
  public boolean changePassword(String username, String oldPassword, String newPassword) {
    User u = userMapper.selectByUsername(username);
    if (u == null) { return false; }
    if (!passwordEncoder.matches(oldPassword, u.getPasswordHash())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "原密码不正确");
    }
    String hash = passwordEncoder.encode(newPassword);
    int updated = userMapper.updatePasswordById(u.getId(), hash);
    return updated > 0;
  }
  public String resolveUserType(User u) {
    try {
      var keys = userRoleMapper.selectRoleKeysByUserId(u.getId());
      if (keys != null && !keys.isEmpty()) {
        if (keys.contains("ADMIN")) return "admin";
        if (keys.contains("TEACHER")) return "teacher";
        if (keys.contains("STUDENT")) return "student";
      }
    } catch (Exception ignored) {}
    String t = u.getUserType();
    if (t == null) return "student";
    t = t.trim().toLowerCase();
    if (t.equals("admin") || t.equals("teacher") || t.equals("student")) return t;
    return "student";
  }
}
