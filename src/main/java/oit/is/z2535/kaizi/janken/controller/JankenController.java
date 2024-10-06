package oit.is.z2535.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    String yourhand = "Pa";
    String result = judge(hand, yourhand);

    model.addAttribute("myhand", hand);
    model.addAttribute("yourhand", "pa");
    model.addAttribute("result", result);
    // ModelMap型変数のmodelにmyhandという名前の変数で，handの値を登録する．
    // ここで値を登録するとthymeleafが受け取り，htmlで処理することができるようになる
    return "janken.html";
  }

  private String judge(String playerHand, String computerHand) {
    // じゃんけんの勝敗判定ロジック
    if (playerHand.equals(computerHand)) {
      return "あいこ";
    } else if ((playerHand.equals("Gu") && computerHand.equals("Ch")) ||
        (playerHand.equals("Ch") && computerHand.equals("Pa")) ||
        (playerHand.equals("Pa") && computerHand.equals("Gu"))) {
      return "勝ち";
    } else {
      return "負け";
    }
  }

}
