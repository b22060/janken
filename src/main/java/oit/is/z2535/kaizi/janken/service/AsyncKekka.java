package oit.is.z2535.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2535.kaizi.janken.model.Match;
//import oit.is.z2535.kaizi.janken.model.MatchInfo;
import oit.is.z2535.kaizi.janken.model.MatchMapper;
import oit.is.z2535.kaizi.janken.model.User;
import oit.is.z2535.kaizi.janken.model.UserMapper;
import oit.is.z2535.kaizi.janken.model.MatchInfoMapper;

@Service
public class AsyncKekka {// DB処理はここで行う

  int fid = -1;
  boolean dbUpdated = false;

  @Autowired
  UserMapper UMapper; // UserMapper使用

  @Autowired
  MatchMapper MMapper; // MatchMapper使用

  @Autowired
  MatchInfoMapper MIMapper; // MatchInfoMapper使用

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  public ArrayList<User> syncShowUserList() {// DBからUser情報を全て持ってくる
    return UMapper.selectAllByUsers();
  }

  public ArrayList<Match> syncShowMatchList() {// DBからMatch情報を全て持ってくる
    return MMapper.selectAllByMatchs();
  }

  public int syncShowUserId(String loginUser) { // DBから名前に対応するIDを取得
    return UMapper.selectByName(loginUser);
  }

  public String syncCheakActiveById(int userid, int opponent) {
    return MIMapper.cheakActiveById(userid, opponent);
  }// DBからtureかつ自分のidの試合の状態を返す

  public int syncActiveRecode() {
    ArrayList<Match> tmp = this.syncShowMatchList();
    return tmp.get(0).getId();
  }

  public void syncInsertMatch(Match match) {
    this.MMapper.insertMatch(match);// 試合結果を格納する
    this.fid = this.syncActiveRecode();
  }

  public void syncUpdateActive(int id) {
    this.MIMapper.updateActive(id);
  }

  @Async
  public void asyncJankenKekka(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.5s休み
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }

        TimeUnit.MILLISECONDS.sleep(100);
        dbUpdated = false;

        TimeUnit.MILLISECONDS.sleep(100);
        //int id = MMapper.selectActiveIdByMatch(); // Activeの試合情報を渡す
        Match match = MMapper.selectActiveByMatch(this.fid);
        emitter.send(match);
        TimeUnit.MILLISECONDS.sleep(200);
        MMapper.updateActive(this.fid); // matchをfalseにする
        TimeUnit.MILLISECONDS.sleep(200);
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowFruitsList complete");
  }
}
