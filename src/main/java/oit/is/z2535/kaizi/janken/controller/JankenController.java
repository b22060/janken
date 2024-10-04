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
}
