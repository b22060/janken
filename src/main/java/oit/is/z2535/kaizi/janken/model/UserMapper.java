package oit.is.z2535.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
  @Select("SELECT username from users")
  ArrayList<User> selectAllByUserName();
}
