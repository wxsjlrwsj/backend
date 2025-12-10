package org.example.chaoxingsystem.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 令牌服务：提供简化版 JWT 的生成与校验
 * 注意：当前实现未加入过期时间与刷新机制，生产环境需扩展 exp/refresh 等逻辑
 */
@Service
public class TokenService {
  private final String secret;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public TokenService(@Value("${security.token.secret:dev-secret}") String secret) {
    this.secret = secret;
  }

  public String generateToken(User user) {
    // 头部固定为 HS256 算法
    String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
    long iat = Instant.now().getEpochSecond();
    // 负载包含用户标识、用户名、用户类型与签发时间
    String payloadJson = "{\"sub\":" + quote(user.getId()) + 
      ",\"username\":" + quote(user.getUsername()) +
      ",\"userType\":" + quote(user.getUserType()) +
      ",\"iat\":" + iat + "}";

    String header = base64UrlEncode(headerJson.getBytes(StandardCharsets.UTF_8));
    String payload = base64UrlEncode(payloadJson.getBytes(StandardCharsets.UTF_8));
    String toSign = header + "." + payload;
    String signature = hmacSha256(toSign, secret);
    return toSign + "." + signature;
  }

  public TokenData parseAndValidate(String token) {
    try {
      String[] parts = token.split("\\.");
      if (parts.length != 3) {
        return null;
      }
      String header = parts[0];
      String payload = parts[1];
      String signature = parts[2];
      // 校验签名是否匹配
      String expected = hmacSha256(header + "." + payload, secret);
      if (!expected.equals(signature)) {
        return null;
      }
      byte[] payloadBytes = Base64.getUrlDecoder().decode(payload);
      @SuppressWarnings("unchecked") Map<String, Object> json = objectMapper.readValue(payloadBytes, Map.class);
      Object sub = json.get("sub");
      Object username = json.get("username");
      Object userType = json.get("userType");
      if (username == null || userType == null) {
        return null;
      }
      Long id = null;
      if (sub != null) {
        try { id = Long.valueOf(String.valueOf(sub)); } catch (Exception ignored) {}
      }
      return new TokenData(id, String.valueOf(username), String.valueOf(userType));
    } catch (Exception e) {
      return null;
    }
  }

  private String quote(Object obj) {
    return "\"" + String.valueOf(obj) + "\"";
  }

  private String base64UrlEncode(byte[] data) {
    return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
  }

  private String hmacSha256(String data, String key) {
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
      byte[] result = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
      return base64UrlEncode(result);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to generate token", e);
    }
  }

  public static class TokenData {
    private final Long id;
    private final String username;
    private final String userType;

    public TokenData(Long id, String username, String userType) {
      this.id = id;
      this.username = username;
      this.userType = userType;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getUserType() { return userType; }
  }
}
