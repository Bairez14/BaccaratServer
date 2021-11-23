import java.util.ArrayList;


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
        playerHand = new ArrayList<Card>();
        bankerHand = new ArrayList<Card>();
        theDealer = new BaccaratDealer();
        theDealer.generateDeck();
        playerHand = theDealer.dealHand();
        bankerHand = theDealer.dealHand();
        
        // totalWinnings = 0.0;
        // currentBet =  0.0;
        // betOn = "";
        // winner = "";
        // natural = -1;
    }

    //This method will determine if the user won or
    //lost their bet and return the amount won or lost based on the
    //value in currentBet.
    public double evaluateWinnings() {
        //this.betOn = clientInfo.betOnWho;
       // playerHand = theDealer.dealHand();
        //bankerHand = theDealer.dealHand();
        //call drawCard method before calculating who one cuz could have possible drawn another card inlfuencing totals
        drawCard(playerHand, bankerHand);
        winner = BaccaratGameLogic.whoWon(playerHand, bankerHand);
        natural = BaccaratGameLogic.naturalWinner(playerHand, bankerHand);

        System.out.println("Beton: " + betOn);
        // if user chose player
        if (winner.equals(betOn) && natural == 1) {
            totalWinnings += currentBet;
        } // if user chose banker
        else if (winner.equals(betOn) && natural == 2) {
            totalWinnings += currentBet;
        } // if user chose draw
        else if (winner.equals(betOn) && natural == 0) {
            totalWinnings = 8*currentBet;
        } else {
            System.out.println("lost");
            totalWinnings -= currentBet;
        }
        return totalWinnings;
    }

    public void drawCard(ArrayList<Card> playerhand, ArrayList<Card> bankerhand){
        Card c = theDealer.drawOne();
        Card noPlayerCard = null;
        if(BaccaratGameLogic.evaluatePlayerDraw(playerhand)){ //player needs to draw another card 
            playerhand.add(theDealer.drawOne());
            System.out.println("Player drew another card");
            pdraw = true;
            if(BaccaratGameLogic.evaluateBankerDraw(bankerhand, c)){ //banker needs to draw another card too
                bankerhand.add(c);
                System.out.println("Banker drew card");
                bdraw = true;
                return;
            }
        } else if(BaccaratGameLogic.evaluateBankerDraw(bankerhand, noPlayerCard)){ // case if player does not need to draw hand but banker does
            bankerhand.add(c);
            System.out.println("Banker drew another card but player did not draw");
            bdraw = true;
        }
    }

}
