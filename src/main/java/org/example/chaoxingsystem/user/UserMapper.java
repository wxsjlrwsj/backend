package org.example.chaoxingsystem.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
  User selectByUsername(@Param("username") String username);
  User selectByEmail(@Param("email") String email);
  User selectById(@Param("id") Long id);
  Long countByUsername(@Param("username") String username);
  Long countByEmail(@Param("email") String email);
  int insert(User user);
  int updatePasswordById(@Param("id") Long id, @Param("passwordHash") String passwordHash);
  int updateProfileById(@Param("id") Long id, @Param("email") String email, @Param("phone") String phone);
  List<User> selectAll();
  List<User> searchByUserType(@Param("userType") String userType,
                              @Param("keyword") String keyword,
                              @Param("offset") int offset,
                              @Param("limit") int limit);
  Long countByUserTypeAndKeyword(@Param("userType") String userType,
                                 @Param("keyword") String keyword);
}
