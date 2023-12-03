package myDirectory;

/**
 * This class extends Deck. It is used to build and sort a Skip Bo deck of cards
 * @author lclem
 *
 */
public class SkipBo extends Deck 
{
	
	/**
	 * This builds a Skip Bo deck
	 */
	public void buildDeck()
	{
		Card[] deckTemp = new Card[162];
		String[] skipBoRank = {"1","2","3","4","5","6","7","8","9","10","11","12","Skip"};
		
		
		    for(int i = 0; i < skipBoRank.length-1; i++)
		    for(int k = 0; k < 12; k++)
			{
				Card cardTemp = new Card(skipBoRank[i],"Skip Bo",skipBoRank[i] + " Skip Bo", (k + i*12) );
				deckTemp[k + i*12] = cardTemp;
			}
			
			for(int i = 144; i < 162;i++)
			{
				Card cardTemp = new Card("Skip","Skip Bo","Skip" + " Skip Bo", i);
				deckTemp[i] = cardTemp;
			} 
			
			this.deck = deckTemp;
	}

	/**
	 * This sorts a Skip Bo Hand based on rank
	 * @param hand - takes in a hand to sort
	 */
	
	public Card[] bubbleSort(Card[] hand)
	{

		int n = hand.length;
		int tempRank;
		int tempRank2;
		
		boolean swapped = true; 
		while(swapped)
		{
			swapped = false; 
			for(int i = 1; i < n; i++)
			{
				if(hand[i-1].getRank() == "Skip")
				{
					tempRank = 13;
				}
				else
					tempRank = Integer.parseInt(hand[i-1].getRank());
				
				if(hand[i].getRank() == "Skip")
				{
					tempRank2 = 13;
				}
				else
					tempRank2 = Integer.parseInt(hand[i].getRank());
			
				
				if( tempRank > tempRank2 )
				{
					exchange(hand,i-1,i);
					swapped = true; 
				}
			}
		}
		
		return hand;	
	}
	

}
