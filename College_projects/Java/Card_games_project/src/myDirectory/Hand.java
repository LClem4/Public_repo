package myDirectory;

/**
 * This class is used for storing/building and using a hand of cards
 * @author lclem
 *
 */
public class Hand 
{

	private Card[] hand; // Stores a Hand of cards
	
	/**
	 * Initially defines the deck of cards. Used with deck deal
	 * @param hand stores an array of cards that will work as the hand
	 */
	public Hand(Card[] hand)
	{
		this.hand = hand;
	}
	
	/**
	 * Used with deck draw to add a card to the hand of cards
	 * @param hand stores an array of cards plus the card drawn from the deck to form a new hand
	 */
	public void setHand(Card[] hand)
	{
		this.hand = hand;
	}
	
	/**
	 * retrieves hand
	 * @return gets hand
	 */
	public Card[] getHand()
	{
		return hand;
	}
	
	/**
	 * prints hand
	 */
	public void printHand()
	{
		for(int i = 0; i < hand.length; i++)
		{
			System.out.println(hand[i].getName());
		}
	}

}
