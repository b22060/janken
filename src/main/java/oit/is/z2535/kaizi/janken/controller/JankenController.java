package oit.is.z2535.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2535.kaizi.janken.model.Janken;
import oit.is.z2535.kaizi.janken.model.Entry;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);

    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String hand, ModelMap model, Principal prin) {
    Janken janken = new Janken();
    String cpuhand = janken.cpuhand();
    String result = janken.judge(hand, cpuhand);

    model.addAttribute("myhand", hand);
    model.addAttribute("yourhand", cpuhand);
    model.addAttribute("result", result);
    // ModelMap型変数のmodelにmyhandという名前の変数で，handの値を登録する．
    // ここで値を登録するとthymeleafが受け取り，htmlで処理することができるようになる
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);

    return "janken.html";
  }

}
