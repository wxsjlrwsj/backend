package org.example.chaoxingsystem.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
  User selectByUsername(@Param("username") String username);
  User selectByEmail(@Param("email") String email);
  Long countByUsername(@Param("username") String username);
  Long countByEmail(@Param("email") String email);
  int insert(User user);
  int updatePasswordById(@Param("id") Long id, @Param("passwordHash") String passwordHash);
  List<User> selectAll();
}
