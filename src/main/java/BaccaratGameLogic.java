import java.util.ArrayList;
import java.util.*;
import java.lang.Math;

public class BaccaratGameLogic {
    public static String winner;
    public static int playerPoints;
    public static int bankerPoints;
    /*The method whoWon will evaluate two hands at the end of the game and return a string
    depending on the winner: “Player”, “Banker”, “Draw”. The method handTotal will take a
    hand and return how many points that hand is worth. The methods evaluateBankerDraw
    and evaluatePlayerDraw will return true if either one should be dealt a third card,
    otherwise return false.*/

   public static String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
       playerPoints = handTotal(hand1);
       bankerPoints = handTotal(hand2);
       int playerAbs = Math.abs(playerPoints-9);
       int bankerAbs = Math.abs(bankerPoints-9);

       if (naturalWinner(hand1, hand2) == 1) {
           winner = "Player";
           return "Player";
       }
       else if (naturalWinner(hand1, hand2) == 2) {
           winner = "Banker";
           return "Banker";
       }
       else if (naturalWinner(hand1, hand2) == 0) {
           winner = "Draw";
           return "Draw";
       }
       else if (playerAbs > bankerAbs) { // check to see who is closer to 9 using math and absolute value method
            winner = "Banker";
           return "Banker";
       }
       else if (playerAbs < bankerAbs) {
            winner = "Player";
           return "Player";
       }
       return "";   
    }

    // extra mathod that finds the natural winner if any
    // hand 1 = playerhand
    // hand2 = bankerhand
    public static int naturalWinner(ArrayList<Card> hand1, ArrayList<Card> hand2) {
        // if both won, theres a draw, we return 0
        if((handTotal(hand1) == 8 || handTotal(hand1) == 9)
                && (handTotal(hand2) == 8 || handTotal(hand2) == 9)) {
            return 0;
        }
        // if only player won we return 1
        else if (handTotal(hand1) == 8 || handTotal(hand1) == 9) {
            return 1;
        }
        //if banker won we return 2
        else if (handTotal(hand2) == 8 || handTotal(hand2) == 9) {
            return 2;
        }
        // not a natural win, return -1;
        return -1;
            
    }

    public static int handTotal(ArrayList<Card> hand) {
        int total = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue() >= 10) {
                total = total + 0;
            } else {
                total = total + hand.get(i).getValue();
            }
        }
        if (total > 9) {
            total = total - 10;
        }
        return total;
    }


    public static boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
        // Next it’s The Banker’s turn:if the bankers first two
        // cards total 7 or more, no more cards are dealt.
        // If The Banker’s cards total 2 or less, The Banker gets one additional
        // card.If The Bankers first two cards total 3,4,5,or 6,
        // it depends on if The Player drew another card and if so,
        // the value of that card to determine if The Banker
        // receives another card.
    
        if (handTotal(hand) >= 7) {
            return false;
        } else if (handTotal(hand) <= 2) {
            return true;
        } else if (handTotal(hand) >= 3 && handTotal(hand) <= 6) { //using chart
            if (playerCard == null) { //Player took no card
                if (handTotal(hand) <= 5) {
                    return true;
                }
            } else if ((playerCard.value == 0 || playerCard.value == 1) && handTotal(hand) == 3) {
                return true;
            } else if ((playerCard.value == 2 || playerCard.value == 3) && handTotal(hand) <= 4) {
                return true;
            } else if ((playerCard.value == 4 || playerCard.value == 5) && handTotal(hand) <= 5) {
                return true;
            } else if ((playerCard.value == 6 || playerCard.value == 7) && handTotal(hand) <= 6) {
                return true;
            } else if (playerCard.value == 8 && handTotal(hand) <= 2) {
                return true;
            } else if (playerCard.value == 9 && handTotal(hand) <= 3) {
                return true;
            }
        }
        return false;  
    }
 

    public static boolean evaluatePlayerDraw(ArrayList<Card> hand) {
        if (handTotal(hand) <= 5) {
            return true;
        }
        // returns false since the total is > than 5
        return false;
        
    }
}