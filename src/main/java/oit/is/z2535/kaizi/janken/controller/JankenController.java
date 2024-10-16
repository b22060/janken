package oit.is.z2535.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2535.kaizi.janken.model.Janken;
import oit.is.z2535.kaizi.janken.model.UserMapper;
import oit.is.z2535.kaizi.janken.model.Entry;
import oit.is.z2535.kaizi.janken.model.User;
import oit.is.z2535.kaizi.janken.model.Match;
import oit.is.z2535.kaizi.janken.model.MatchMapper;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @Autowired //UserMapper内の関数を使用
  UserMapper usermappser;

  @Autowired //MatchMapper内の関数を使用
  MatchMapper matchmappser;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    ArrayList<User> user = usermappser.selectAllByUserName();
    ArrayList<Match> match = matchmappser.selectAllByMatchs();
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("userName", user);
    model.addAttribute("matchs", match);

    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String hand, ModelMap model) {
    Janken janken = new Janken();
    String cpuhand = janken.cpuhand();
    String result = janken.judge(hand, cpuhand);

    model.addAttribute("myhand", hand);
    model.addAttribute("yourhand", cpuhand);
    model.addAttribute("result", result);
    // ModelMap型変数のmodelにmyhandという名前の変数で，handの値を登録する．
    // ここで値を登録するとthymeleafが受け取り，htmlで処理することができるようになる

    model.addAttribute("entry", this.entry);

    return "janken.html";
  }

}
