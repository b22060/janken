package oit.is.z2535.kaizi.janken.model;

import java.util.Random;

public class Janken {
  String result;
  public Janken() {

  }

  public String getAve() {
    return result;
  }

  public void setAve(String result) {
    this.result = result;
  }

  public String judge(String playerHand, String computerHand) {
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

  public String cpuhand() {
    Random x = new Random();
    int randomNumber = x.nextInt(3);
    String hand;

    if(randomNumber == 0){
      hand = "Gu";
    }else if(randomNumber == 1){
      hand = "Ch";
    } else {
      hand = "Pa";
    }

    return hand;
  }

}
