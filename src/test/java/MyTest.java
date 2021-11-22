import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

//A minimum of two unit tests per required method and one per class constructor
class MyTest {

	@Test
	void cardTest() {
		Card c = new Card("diamond", 7);
		assertEquals(7, c.getValue(), "Testing constructor value");
		assertEquals("diamond", c.getSuite(), "Testing constructor suite");
	}

	@Test
	void dealerConstructortest() {
		BaccaratDealer bd = new BaccaratDealer();
		bd.generateDeck();
		assertEquals(52, bd.deckSize(), "Testing dealer constructor");
	}

	@Test
	void drawOneTest(){
		BaccaratDealer bd = new BaccaratDealer();
		bd.generateDeck();
		bd.drawOne();
		assertEquals(51, bd.deckSize(), "incorrect deck size for draw One");
		assertNotEquals(49, bd.deckSize(), "false: incorrect deck size draw One test");
	}

	@Test 
	void dealHandTest(){
		BaccaratDealer bd = new BaccaratDealer();
		bd.generateDeck();
		ArrayList<Card> test = bd.dealHand();
		assertFalse(test.isEmpty());
		assertEquals(2, test.size(), "Incorrect size");
	}

	@Test
	void shuffleDeckTest(){
		BaccaratDealer bd = new BaccaratDealer();
		bd.generateDeck();
		bd.shuffleDeck();
		BaccaratDealer bd2 = new BaccaratDealer();
		bd2.generateDeck();
		bd2.shuffleDeck();
		assertNotEquals(bd.getDeck().get(0).getValue(), bd2.getDeck().get(0).getValue() ,"shuffle deck failed");
		//assertNotEquals(bd.getDeck().get(0).getSuite(), bd2.getDeck().get(0).getSuite() ,"shuffle deck failed");
	}

	
	

}
