package oit.is.z2535.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
  @Select("SELECT * from users") //Userの全部のデータを取ってくるメソッド
  ArrayList<User> selectAllByUsers();

  @Select("SELECT * from users where id = #{id}") //特定のidを表示
  ArrayList<User> selectById(int id);
}
