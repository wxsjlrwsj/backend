package org.example.chaoxingsystem.user;

import jakarta.validation.Valid;
import org.example.chaoxingsystem.user.dto.ApiResponse;
import org.example.chaoxingsystem.user.dto.ProfileResponse;
import org.example.chaoxingsystem.user.dto.UpdatePasswordRequest;
import org.example.chaoxingsystem.user.dto.UpdateProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/common")
public class CommonController {
  private final UserService userService;

  public CommonController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/profile")
  @PreAuthorize("hasAnyRole('STUDENT','TEACHER','ADMIN')")
  public ResponseEntity<ApiResponse<ProfileResponse>> profile(Authentication authentication) {
    String username = authentication.getName();
    var info = userService.getUserInfoByUsername(username);
    if (info == null) {
      return ResponseEntity.status(404).body(ApiResponse.error(404, "用户不存在"));
    }
    var user = userService.getByUsername(username);
    ProfileResponse resp = new ProfileResponse(info.getUsername(), info.getRealName(), info.getUserType(), user != null ? user.getPhone() : null, user != null ? user.getEmail() : null, info.getAvatar());
    return ResponseEntity.ok(ApiResponse.success("获取成功", resp));
  }

  @PutMapping("/profile")
  @PreAuthorize("hasAnyRole('STUDENT','TEACHER','ADMIN')")
  public ResponseEntity<ApiResponse<Void>> updateProfile(Authentication authentication, @Valid @RequestBody UpdateProfileRequest request) {
    String username = authentication.getName();
    boolean ok = userService.updateProfile(username, request.getEmail(), request.getPhone());
    if (!ok) {
      return ResponseEntity.status(404).body(ApiResponse.error(404, "用户不存在"));
    }
    return ResponseEntity.ok(ApiResponse.success("更新成功", null));
  }

  @PutMapping("/password")
  @PreAuthorize("hasAnyRole('STUDENT','TEACHER','ADMIN')")
  public ResponseEntity<ApiResponse<Void>> changePassword(Authentication authentication, @Valid @RequestBody UpdatePasswordRequest request) {
    String username = authentication.getName();
    boolean ok = userService.changePassword(username, request.getOldPassword(), request.getNewPassword());
    if (!ok) {
      return ResponseEntity.status(404).body(ApiResponse.error(404, "用户不存在"));
    }
    return ResponseEntity.ok(ApiResponse.success("密码修改成功", null));
  }
}
