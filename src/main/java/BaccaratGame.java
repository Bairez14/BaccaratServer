import java.util.ArrayList;

// BACCARATINFO CLASS
// String betOn;
//     double currentBet;
//     ArrayList<String> playerHand = new ArrayList<String>();
//     ArrayList<String> bankerHand = new ArrayList<String>();
//     //int port;
//     //String ipAddress;
//     Boolean quit;
//     double totalEarnings;
//     double currentEarnings;
//     String gameResult;


public class BaccaratGame{

    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    BaccaratDealer theDealer;
    double currentBet;
    double totalWinnings;
    String betOn;
    String winner;
    boolean pdraw = false;
    boolean bdraw = false;
    int natural;

    
    BaccaratGame() {
        // playerHand = new ArrayList<Card>();
        // bankerHand = new ArrayList<Card>();
        // theDealer = new BaccaratDealer();
        // totalWinnings = 0.0;
        // this.currentBet = currentBet;
    }

    //This method will determine if the user won or
    //lost their bet and return the amount won or lost based on the
    //value in currentBet.
    public double evaluateWinnings(BaccaratInfo clientInfo) {
        this.betOn = clientInfo.betOnWho;
        playerHand = theDealer.dealHand();
        bankerHand = theDealer.dealHand();
        //call drawCard method before calculating who one cuz could have possible drawn another card inlfuencing totals
        drawCard(playerHand, bankerHand);
        winner = BaccaratGameLogic.whoWon(playerHand, bankerHand);
        natural = BaccaratGameLogic.naturalWinner(playerHand, bankerHand);


        // if user chose player
        if (winner == "Player" && betOn == "Player") {
            totalWinnings += currentBet;
        } // if user chose banker
        else if (winner == "Banker" && betOn == "Banker") {
            totalWinnings += currentBet;
        } // if user chose draw
        else if (winner == "Draw" && betOn == "draw") {
            totalWinnings = currentBet * 8;
        }
        return totalWinnings;
    }

    public void drawCard(ArrayList<Card> playerhand, ArrayList<Card> bankerhand){
        Card c = theDealer.drawOne();
        Card noPlayerCard = null;
        if(BaccaratGameLogic.evaluatePlayerDraw(playerhand)){ //player needs to draw another card 
            playerhand.add(theDealer.drawOne());
            pdraw = true;
            if(BaccaratGameLogic.evaluateBankerDraw(bankerhand, c)){ //banker needs to draw another card too
                bankerhand.add(c);
                bdraw = true;
                return;
            }
        } else if(BaccaratGameLogic.evaluateBankerDraw(bankerhand, noPlayerCard)){ // case if player does not need to draw hand but banker does
            bankerhand.add(c);
            bdraw = true;
        }
    }

}


// theDealer.generateDeck();
// playerHand = theDealer.dealHand();
// bankerHand = theDealer.dealHand();
// int playerPoints = 0;
// int bankerPoints = 0;
// boolean drewCard = false;

// // player will go first
// for (int i = 0; i < playerHand.size(); i++) {
// playerPoints = playerPoints + playerHand.get(i).getValue();
// }
// if (playerPoints == 9 || playerPoints == 8) {
// // natural win
// }
// else if (playerPoints <= 5) {
// playerHand.add(theDealer.drawOne());
// drewCard = true;
// }
// else if (playerPoints > 9) {
// if (playerPoints == 10) {
// playerPoints = 1;
// } else {
// playerPoints = playerPoints - 10;
// }
// }

// // banker's turn
// for (int i = 0; i < bankerHand.size(); i++) {
// bankerPoints = bankerPoints + bankerHand.get(i).getValue();
// }
// // check if both player and banker won with 8 or 9 pts
// if ((playerPoints == 8 || playerPoints == 9) && (bankerPoints == 8 ||
// bankerPoints == 9)){
// // if user didn't pick draw, the user lost
// }
// if (bankerPoints == 9 || bankerPoints == 8) {
// //natural win
// }
// else if (bankerPoints <= 2) {
// bankerHand.add(theDealer.drawOne());
// }
// // If The Bankers first two cards total 3, 4, 5, or 6, it depends on if The
// Player drew another
// //card and if so, the value of that card to determine if The Banker receives
// another card.
// else if ((bankerPoints>= 3 && bankerPoints <= 6) && drewCard == true) {
// bankerHand.add(theDealer.drawOne());
// }

// return 0;
// }