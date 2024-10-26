package oit.is.z2535.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchInfoMapper {
  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertAllMatchInfo(MatchInfo chamber);

  @Select("SELECT * from matchinfo")
  ArrayList<MatchInfo> selectAllByMatchInfo();

  @Select("SELECT * from matchinfo where isActive = true") //isActiveがtureの試合をselect
  ArrayList<MatchInfo> selectActiveByMatchInfo();
}
