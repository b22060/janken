package oit.is.z2535.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
  @Select("SELECT * from users") //Userの全部のデータを取ってくるメソッド
  ArrayList<User> selectAllByUsers();

  @Select("SELECT userName from users where id = #{id}") //特定のidで特定の名前を表示
  String selectById(int id);

  @Select("SELECT id from users where userName = #{userName}") // 特定の名前で特定のidを表示
  int selectByName(String userName);

  @Select("SELECT * from users where id = #{id}") // 特定のidで全部の情報を表示
  User selectAllById(int id);
}
