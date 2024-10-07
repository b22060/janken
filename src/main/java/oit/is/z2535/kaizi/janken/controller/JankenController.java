package oit.is.z2535.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2535.kaizi.janken.model.Janken;

@Controller
public class JankenController {

  @GetMapping("/janken") /* formから入力がなければ */
  public String janken() {
    return "janken.html";
  }

  @PostMapping("/janken") /* formからusernameに入力があれば */
  public String janken(@RequestParam String username, ModelMap model) {
    model.addAttribute("username", username);
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
    return "janken.html";
  }

}
