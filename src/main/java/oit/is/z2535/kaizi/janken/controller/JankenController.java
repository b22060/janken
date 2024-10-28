package oit.is.z2535.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

//import oit.is.z2535.kaizi.janken.model.Janken;
import oit.is.z2535.kaizi.janken.model.UserMapper;
import oit.is.z2535.kaizi.janken.service.AsyncKekka;
import oit.is.z2535.kaizi.janken.model.Entry;
import oit.is.z2535.kaizi.janken.model.User;
import oit.is.z2535.kaizi.janken.model.Match;
import oit.is.z2535.kaizi.janken.model.MatchInfo;
import oit.is.z2535.kaizi.janken.model.MatchMapper;
import oit.is.z2535.kaizi.janken.model.MatchInfoMapper;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @Autowired // UserMapper内の関数を使用
  UserMapper usermappser;

  @Autowired // MatchMapper内の関数を使用
  MatchMapper matchmappser;

  @Autowired // MatchInfoMapper内の関数を使用
  MatchInfoMapper matchinfomappser;

  @Autowired // AsyncKekka内の関数を使用
  AsyncKekka DBInfo;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    ArrayList<User> user = usermappser.selectAllByUsers();
    ArrayList<Match> match = matchmappser.selectAllByMatchs();
    ArrayList<MatchInfo> info = matchinfomappser.selectActiveByMatchInfo();
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("userName", user);
    model.addAttribute("matchs", match);
    model.addAttribute("info", info);

    return "janken.html";
  }

  @GetMapping("/fight")
  public String jankengame(@RequestParam Integer id, @RequestParam String hand, Principal prin, ModelMap model) {
    MatchInfo info = new MatchInfo();
    String loginUser = prin.getName(); // ログイン名を取得
    int loginUser_id = usermappser.selectByName(loginUser);// ID取得
    boolean active = true;
    info.setUser1(loginUser_id); // 自身のID
    info.setUser2(id); // 相手のID
    info.setUser1Hand(hand); // 自分の手
    info.setIsActive(active); // isActiveをtrueに
    model.addAttribute("loginUser", loginUser);
    // matchinfomappser.insertAllMatchInfo(info); //MatchInfoにinsert

    if (matchinfomappser.cheakActiveById(id, loginUser_id) == "TRUE") {// 自分と相手のidでactiveの試合があるか？
      System.out.println(id);
      int matching = matchinfomappser.selectActiveById(id, loginUser_id); // 相手の情報があるレコードを取り出す
      System.out.println(matching);
      String opponenthand = matchinfomappser.selectOpponenthandById(matching);// 相手の手を取り出す
      Match match = new Match(loginUser_id, id, hand, opponenthand, true);// 試合の情報を格納
      DBInfo.syncInsertMatch(match);
      DBInfo.syncUpdateActive(matching);
    } else {
      matchinfomappser.insertAllMatchInfo(info);
    }

    ArrayList<Match> matches = DBInfo.syncShowMatchList();
    model.addAttribute("matches", matches);// 全試合情報
    model.addAttribute("id", id);// 相手のid
    // model.addAttribute("", opponent);//相手の名前

    return "wait.html";
  }

  @GetMapping("/match")
  @Transactional
  public String match(@RequestParam Integer id, ModelMap model) {
    User userdata = usermappser.selectAllById(id);
    model.addAttribute("userdata", userdata);

    return "match.html";
  }

  @GetMapping("/step9")
  public SseEmitter sample59() { // htmlが読み込まれた時に呼び出される。
    final SseEmitter sseEmitter = new SseEmitter();
    this.DBInfo.asyncJankenKekka(sseEmitter);
    return sseEmitter;
  }

}
