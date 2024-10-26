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

import oit.is.z2535.kaizi.janken.model.Janken;
import oit.is.z2535.kaizi.janken.model.UserMapper;
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
  public String jankengame(@RequestParam Integer id,@RequestParam String hand, Principal prin, ModelMap model) {
    Janken janken = new Janken();
    String cpuhand = janken.cpuhand();
    //String result = janken.judge(hand, cpuhand);
    String userid = usermappser.selectById(id); //cpuの名前を取得
    //Match match = new Match();
    MatchInfo info = new MatchInfo();
    String loginUser = prin.getName(); //ログイン名を取得
    int loginUser_id = usermappser.selectByName(loginUser);
    boolean active = true;
    /*match.setUser1(loginUser_id);//自分のid
    match.setUser2(id);//cpuのid
    match.setUser1Hand(hand);//自分の手
    match.setUser2Hand(cpuhand);//相手の手
    match.setResult(result);//結果
    match.setIsActive(active);
    model.addAttribute("name", userid);//
    model.addAttribute("myhand", hand);//htmlに渡す
    model.addAttribute("yourhand", cpuhand);//
    model.addAttribute("result", result);//
    matchmappser.insertAllMatch(match);//DBにinsert*/
    // ModelMap型変数のmodelにmyhandという名前の変数で，handの値を登録する．
    // ここで値を登録するとthymeleafが受け取り，htmlで処理することができるようになる
    info.setUser1(loginUser_id);
    info.setUser2(id);
    info.setUser1Hand(hand);
    info.setIsActive(active);
    model.addAttribute("loginUser",loginUser);
    matchinfomappser.insertAllMatchInfo(info);
    return "wait.html";
  }

  @GetMapping("/match")
  @Transactional
  public String match(@RequestParam Integer id, ModelMap model) {
    User userdata = usermappser.selectAllById(id);
    model.addAttribute("userdata", userdata);

    return "match.html";
  }

}
