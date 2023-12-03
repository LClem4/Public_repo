package myDirectory;

/**
 * This class extends Deck. It is used to build and sort a Standard deck of cards
 * @author lclem
 *
 */
public class Standard extends Deck 
{
	
	/**
	 * This builds a standard deck of cards
	 */
	public void buildDeck()
	{
		Card[] deckTemp = new Card[52];
		String[] standardSuit = {"Clubs","Diamonds","Spades","Hearts"};
	    String[] standardRank = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
	    
	   
	    for(int i = 0; i < standardRank.length;i++)
	    for(int j = 0; j < standardSuit.length;j++)
	    {
	    	Card cardTemp = new Card(standardRank[i],standardSuit[j],standardRank[i] + " of " + standardSuit[j],(standardSuit.length*i + j));
	    	deckTemp[standardSuit.length*i + j] = cardTemp;
	    }
	    
	    this.deck = deckTemp;
	}
	
	/**
	 * This sorts a Standard hand by
	 * @param hand - takes in a hand to sort
	 */
	public Card[] insertionSort(Card[] hand)
	{

			int n = hand.length;
			for (int i = 1; i < n; i++) 
				for (int j = i; j > 0; j--) 
				{
				    if(hand[j-1].getValue() > hand[j].getValue())
					{
						exchange(hand,j-1,j); 	
					} 
					else 
						break; 
				}
			
			return hand;
	} 

}
