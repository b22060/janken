package oit.is.z2535.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
//import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchInfoMapper {
  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertAllMatchInfo(MatchInfo chamber);

  @Select("SELECT * from matchinfo")
  ArrayList<MatchInfo> selectAllByMatchInfo();

  @Select("SELECT * from matchinfo where isActive = true") // isActiveがtureの試合をselect
  ArrayList<MatchInfo> selectActiveByMatchInfo();

  @Select("SELECT isActive FROM matchinfo WHERE isActive = TRUE AND (user1 = #{userid} AND user2 = #{opponentid})")
  String cheakActiveById(int userid, int opponentid);// isActiveがtureの自分の試合の状態を返す。

  @Select("SELECT id FROM matchinfo WHERE isActive = TRUE AND (user1 = #{opponentid} AND user2 = #{userid})")
  int selectActiveById(int opponentid, int userid);// 相手の情報が入っているレコードのidを入手

  @Select("SELECT user1Hand FROM matchinfo WHERE id = #{id};")
  String selectOpponenthandById(int id);// 対称のレコードのhandを取り出す

  @Update("UPDATE matchinfo SET isActive = FALSE where id = #{id}") // FALSEにする
  boolean updateActive(int id);

  @Update("UPDATE matchinfo SET isActive = TRUE WHERE isActive =TRUE AND (user1 = #{opponentid} AND user2 =#{userid});")
  boolean checkActive(int opponentid, int userid);
}
