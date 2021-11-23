import java.util.ArrayList;
import java.util.*;


public class BaccaratDealer{
    //data members
    ArrayList<Card> deck;

    BaccaratDealer(){
        deck = new ArrayList<Card>(52);
    }

    public void generateDeck() {
        // 4 suites
        //spades
        for (int i = 1; i < 14; i++) {
            deck.add(new Card("spades", i));
        }
        //clubs
        for (int i = 1; i < 14; i++) {
            deck.add(new Card("clubs", i));
        }
        
        //hearts
        for (int i = 1; i < 14; i++) {
            deck.add(new Card("hearts", i));
        }
        //diamonds
        for (int i = 1; i < 14; i++) {
            deck.add(new Card("diamonds", i));
        }

        // 13 numbers per suite
        // ace = 1 point, face and 10 = 0, everything else is worth their number (2-9)

        shuffleDeck();
    }
    // give two cards
    public ArrayList<Card> dealHand() {
        ArrayList<Card> dealt = new ArrayList<Card>();
        shuffleDeck();
        dealt.add(drawOne());
        dealt.add(drawOne());
        System.out.println("size: " + dealt.size());
        return dealt;
    }

    // random card returned
    public Card drawOne() {
        shuffleDeck();
        Card c;
        c = deck.get(0);
        deck.remove(0);
        return c;
    }
    
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    //returns deck size
    public int deckSize() {
        return deck.size();
    }

    //getter for data member
    public ArrayList<Card> getDeck(){
        return this.deck;
    }
}