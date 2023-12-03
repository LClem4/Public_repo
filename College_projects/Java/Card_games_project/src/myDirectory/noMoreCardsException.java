package myDirectory;

/**
 * This is used to throw an error if a deck of cards runs out of cards
 * used in deal, draw, and decreaseArray in the Deck class
 * @author lclem
 *
 */
public class noMoreCardsException extends Error
{
	private static final long serialVersionUID = 1L; // It said I needed this for some reason

	    /**
	     * Used to throw an error message that there are no more cards left in the deck
	     * @param errorMessage
	     */
		public noMoreCardsException(String errorMessage) 
		{
	        super(errorMessage);
	    }
}
