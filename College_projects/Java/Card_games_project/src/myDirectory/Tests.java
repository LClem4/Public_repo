package myDirectory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This tests all the various coding elements
 * @author lclem
 *
 */
class Tests 
{

	/**
	 * Tests SkipBo class
	 */
	@Test
	void skipBoTest()
	{
		SkipBo deck1 = new SkipBo();
		deck1.buildDeck();
		
		assertEquals("1 Skip Bo",deck1.deck[0].getName()); // Build deck test 1
		assertEquals("2 Skip Bo",deck1.deck[12].getName()); // Build deck test 2
		assertEquals("Skip Skip Bo",deck1.deck[161].getName()); // Build deck test 3
		
		for(int i = 0; i < deck1.deck.length; i++)
		{
			assertEquals(i,deck1.deck[i].getValue()); // tests value
		}
		
		// Tests bubble sort (tests rank)
		Card card1 = new Card("1","Skip Bo","1 Skip Bo",0);
		Card card2 = new Card("4","Skip Bo","4 Skip Bo",1);
		Card card3 = new Card("6","Skip Bo","6 Skip Bo",2);
		Card card4 = new Card("9","Skip Bo","9 Skip Bo",3);
		Card card5 = new Card("11","Skip Bo","11 Skip Bo",4);
		Card card6 = new Card("Skip","Skip Bo","Skip Skip Bo",5);
		
		Card[] cardArray = new Card[] {card3,card6,card1,card5,card4,card2};
		Hand hand1 = new Hand(cardArray);
		deck1.bubbleSort(hand1.getHand());
		
		assertEquals("1",hand1.getHand()[0].getRank()); 
		assertEquals("4",hand1.getHand()[1].getRank());
		assertEquals("6",hand1.getHand()[2].getRank());
		assertEquals("9",hand1.getHand()[3].getRank());
		assertEquals("11",hand1.getHand()[4].getRank());
		assertEquals("Skip",hand1.getHand()[5].getRank());
	
	}

	/**
	 * Tests Standard class
	 */
	@Test
	void StandardTest()
	{
		Standard deck1 = new Standard();
		deck1.buildDeck();
		
		assertEquals("2 of Clubs",deck1.deck[0].getName()); // Build deck test 1
		assertEquals("8 of Spades",deck1.deck[26].getName()); // Build deck test 2
		assertEquals("Ace of Hearts",deck1.deck[51].getName()); // Build deck test 3
		
		for(int i = 0; i < deck1.deck.length; i++)
		{
			assertEquals(i,deck1.deck[i].getValue()); // tests value
		}
		
		deck1.shuffle();
		
		// Tests shuffle
		assertNotEquals("2 of Clubs",deck1.deck[0].getName()); // shuffle test 1
		assertNotEquals("5 of Clubs",deck1.deck[12].getName()); // shuffle test 2
		assertNotEquals("7 of Diamonds",deck1.deck[21].getName()); // shuffle test 3
		assertNotEquals("9 of Spades",deck1.deck[30].getName()); // shuffle test 4
		assertNotEquals("Ace of Hearts",deck1.deck[51].getName()); // shuffle test 5
		
		// Tests insertion sort (tests value)
		Card card1 = new Card("2","Clubs","Ace of Clubs",0);
		Card card2 = new Card("5","Spades","5 of Spades",1);
		Card card3 = new Card("8","Diamonds","8 of Diamonds",2);
		Card card4 = new Card("Jack","Diamonds","Ace of Diamonds",3);
		Card card5 = new Card("Ace","Spades","Ace of Spades",4);
		
		Card[] cardArray = new Card[] {card3,card2,card1,card5,card4};
		Hand hand1 = new Hand(cardArray);
		deck1.insertionSort(hand1.getHand());
		
		for(int i = 0; i < hand1.getHand().length;i++)
			assertEquals(i,hand1.getHand()[i].getValue()); 
		
	}
	
	/**
	 * Tests Uno class 
	 */
	@Test
	void UnoTest()
	{
		Uno deck1 = new Uno();
		deck1.buildDeck();
		
		assertEquals("Red 0",deck1.deck[0].getName()); // Build deck test 1
		assertEquals("Orange 1",deck1.deck[53].getName()); // Build deck test 2
		assertEquals("Blue +2",deck1.deck[99].getName()); // Build deck test 3
		
		for(int i = 0; i < deck1.deck.length; i++)
		{
			assertEquals(i,deck1.deck[i].getValue()); // tests value
		}
		
		// tests selection sort (tests suit and then rank as secondary)
		Card card1 = new Card("1","Blue","Blue 1",0);
		Card card2 = new Card("2","Orange","Orange 2",1);
		Card card3 = new Card("4","Red","Red 4",2);
		Card card4 = new Card("6","Green","Green 6",3);
		Card card5 = new Card("7","Orange","Orange 7",4);
		Card card6 = new Card("9","Blue","Blue 9",5);
		Card card7 = new Card("Reverse","Red","Red Reverse",6);
		
		Card[] cardArray = new Card[] {card3,card6,card1,card5,card4,card2,card7};
		Hand hand1 = new Hand(cardArray);
		deck1.selectionSort(hand1.getHand());
		
		assertEquals("Blue 1",hand1.getHand()[0].getName()); 
		assertEquals("Blue 9",hand1.getHand()[1].getName());
		assertEquals("Green 6",hand1.getHand()[2].getName());
		assertEquals("Orange 2",hand1.getHand()[3].getName());
		assertEquals("Orange 7",hand1.getHand()[4].getName());
		assertEquals("Red 4",hand1.getHand()[5].getName());
		assertEquals("Red Reverse",hand1.getHand()[6].getName());
	}
	
	/**
	 * Tests Card class
	 */
	@Test
	void CardTest()
	{
		Card card1 = new Card("2","Clubs","2 of Clubs",1);
		
		// tests creation of card
		assertEquals("2",card1.getRank()); // rank
		assertEquals("Clubs",card1.getSuit()); // suit
		assertEquals("2 of Clubs",card1.getName()); // name
		assertEquals(1,card1.getValue()); // value
		
	
	}
	
	/**
	 * Tests Hand class
	 */
	@Test
	void HandTest()
	{
		
		Standard deck1 = new Standard();
		deck1.buildDeck();
		
		Card aceHeart = new Card("Ace","Hearts","Ace of Hearts",51);
		Card aceSpade = new Card("Ace","Spades","Ace of Spades",50);
		Card aceDiamond = new Card("Ace","Diamonds","Ace of Diamonds",49);
		Card[] hand2 = new Card[] {aceHeart,aceSpade,aceDiamond};
		
		Hand hand1 = new Hand(deck1.deal(2)); 
			assertEquals(50,deck1.deck.length); // tests if deck was resized
			assertEquals(2,hand1.getHand().length); // tests if hand was resized
		hand1.setHand(deck1.draw(hand1)); 
			assertEquals(49,deck1.deck.length); // tests if deck was resized
			assertEquals(3,hand1.getHand().length); // tests if hand was resized
		
		for(int i = 0;i < hand1.getHand().length;i++) // testing whether hand1 was created properly or not
			{
			assertEquals(hand2[i].getRank(),hand1.getHand()[i].getRank()); // rank test
			assertEquals(hand2[i].getSuit(),hand1.getHand()[i].getSuit()); // suit test
			assertEquals(hand2[i].getName(),hand1.getHand()[i].getName()); // name test
			assertEquals(hand2[i].getValue(),hand1.getHand()[i].getValue()); // value test
			}
		
	
	}
	
	/**
	 * Tests to see if error works
	 */
	@Test
	void noMoreCardsException() 
	{
		
		Standard deck1 = new Standard();
		deck1.buildDeck();
			
		boolean thrown1 = false;
		
		try // Tests to see if an error is thrown for deal
		{
			Hand hand1 = new Hand(deck1.deal(53));
		} 
		catch (noMoreCardsException e)
		{
			thrown1 = true;
		}
		
		assertTrue(thrown1);
		
		Standard deck2 = new Standard();
		deck2.buildDeck();
		
		boolean thrown2 = false;
		
		try // Tests to see if an error is thrown for draw
		{
			Hand hand2 = new Hand(deck2.deal(52));
			hand2.setHand(deck1.draw(hand2));
		} 
		catch (noMoreCardsException e)
		{
			thrown2 = true;
		}
	
		assertTrue(thrown2);
	}
}
