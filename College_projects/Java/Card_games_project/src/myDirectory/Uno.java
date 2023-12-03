package myDirectory;

/**
 * This class extends Deck. It is used to build and sort an Uno deck of cards
 * @author lclem
 *
 */
public class Uno extends Deck
{
	/**
	 * This builds an Uno deck of cards
	 */
	public void buildDeck()

	{
		Card[] deckTemp = new Card[100];
		String[] unoRank = {"0,","1","2","3","4","5","6","7","8","9","Skip","Reverse","+2","1","2","3","4","5","6","7","8","9","Skip","Reverse","+2"};
		String[] unoSuit = {"Red","Orange","Green","Blue"};
		
		for(int j = 0; j < unoSuit.length; j++)
	    {
			Card cardTemp = new Card("0",unoSuit[j],unoSuit[j] + " 0",j);
			deckTemp[j] = cardTemp;
		
	    }
		
	    for(int i = 1; i < unoRank.length; i++)
		for(int j = 0; j < unoSuit.length; j++)
		{
			Card cardTemp = new Card(unoRank[i],unoSuit[j],unoSuit[j] + " " + unoRank[i],((unoSuit.length*i + j)));
			deckTemp[(unoSuit.length*i + j)] = cardTemp;
		}
		
	    this.deck = deckTemp;
	}
	
	/**
	 * This sorts an Uno deck of Cards using selection sort
	 * @param hand - takes in a hand to sort
	 */
	public Card[] selectionSort(Card[] hand)
	{	
		int alength = hand.length;
		for ( int i = 0; i < alength-1; i++)
		{
			int jMin = i;
			
			for (int j = i+1; j < alength; j++)
			{
				if(hand[jMin].getName().compareTo(hand[j].getName()) > 0)
				    {
						jMin = j;
					
					}
			}
			if (jMin != i)
			{
				exchange(hand, i, jMin);
			}	
		}
		
		return hand;
	}
}
