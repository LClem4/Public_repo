package myDirectory;
/**
 * This class is used for storing and manipulating a deck of cards
 * @author lclem
 *
 */
public class Deck
{

	protected Card[] deck; // Stores a deck of Cards
	
	/**
	 * This shuffles a deck cards
	 */
	public void shuffle()
	{
		int n = this.deck.length;
		
		for(int i = 0;i < n;i++)
		{
			int r = i + (int) (Math.random() * (n-i));
			Card temp = this.deck[i];
			this.deck[i] = this.deck[r];
			this.deck[r] = temp;
		}
		
	}

	/**
	 * This deals out a hand of cards
	 * @param int size determines size of hand
	 * @return array of cards
	 */
	public Card[] deal(int size) 
	{
		Card[] hand = new Card[size];
		
		for(int i = 0;i < hand.length; i++)
		{
			try
			{
				hand[i] = this.deck[this.deck.length-1];
			}
			catch (Exception e)
			{
				throw new noMoreCardsException("No Cards Left");
			}
			
				decreaseArray();
		}
	
		return hand;
	}

	/**
	 * This deals a single card to a hand
	 * @param Hand hand determines which the card is dealt to
	 * @return array of cards
	 */
	public Card[] draw(Hand hand) 
	{
		Card[] handTemp = hand.getHand();
		Card[] newHand = new Card[handTemp.length+1];
		
		for(int i = 0;i < handTemp.length; i++)
		{
			newHand[i] = handTemp[i];
		}
		
		try
		{
			newHand[newHand.length-1] = this.deck[this.deck.length-1];
		}
		catch (Exception e)
		{
			throw new noMoreCardsException("No Cards Left");
		}
			decreaseArray();
			
		return newHand;
		
	}

	/**
	 * This is used in deal and draw to resize the deck of cards
	 * 
	 */
	private void decreaseArray() 
	{
		try
		{
			Card[] arrayNew = new Card[this.deck.length-1];

			for(int i = 0;i < arrayNew.length; i++)
			{
				arrayNew[i] = this.deck[i];
			}
	
			this.deck = arrayNew;
		}
		catch (Exception e)
		{
  	        e.printStackTrace();
		}
	
	}
 
	/**
	 * This is used in sub-classes to exchange cards in their respective sorting methods
	 * @param Card[] data, int i, int j
	 */
	protected static void exchange(Card[] data, int i, int j)
	{
		Card temp = data[j];
		data[j] = data[i];
		data[i] = temp;
	
	}
}
