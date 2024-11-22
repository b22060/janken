package oit.is.z2535.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {
  @Select("SELECT * from matches")
  ArrayList<Match> selectAllByMatchs();

  @Insert("INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

  @Insert("INSERT INTO matches (user1,user2,user1Hand,user2Hand,result,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{result},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertAllMatch(Match match);

  @Select("SELECT * FROM matches WHERE isActive = true")
  ArrayList<Match> selectActiveIdByMatch();

  @Select("SELECT * FROM matches WHERE id = #{id}")
  Match selectActiveByMatch(int id);

  @Update("UPDATE matches SET isActive = FALSE where id = #{id}")//FALSEにする
  void updateActive(int id);
}
