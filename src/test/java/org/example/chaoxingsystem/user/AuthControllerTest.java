package org.example.chaoxingsystem.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void registerLoginResetFlow() throws Exception {
    String registerJson = "{\"username\":\"student1\",\"password\":\"password123\",\"userType\":\"student\",\"email\":\"student1@example.com\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(registerJson))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("注册成功"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist());

    mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(registerJson))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400));

    String loginJson = "{\"username\":\"student1\",\"password\":\"password123\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginJson))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
      .andExpect(MockMvcResultMatchers.jsonPath("$.data.token").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.data.userInfo.username").value("student1"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.data.userInfo.userType").value("student"));

    String resetJson = "{\"username\":\"student1\",\"newPassword\":\"newpass123\",\"email\":\"student1@example.com\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/api/reset-password")
        .contentType(MediaType.APPLICATION_JSON)
        .content(resetJson))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("密码重置成功"));

    String loginNewJson = "{\"username\":\"student1\",\"password\":\"newpass123\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginNewJson))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));

    String badLoginJson = "{\"username\":\"student1\",\"password\":\"badbad\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(badLoginJson))
      .andExpect(MockMvcResultMatchers.status().isUnauthorized())
      .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(401));
  }
}