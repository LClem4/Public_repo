package myDirectory;

/**
 * This class is used for storing/building and using a card
 * @author lclem
 *
 */
public class Card 
{

	 private String rank; // stores rank of card
	 private String suit; // stores suit of card
	 private String name; // stores name of card (rank and suit)
	 private int value; // stores a value for the card

	/**
	 * Builds a card setting the initial values
	 * @param rank
	 * @param suit
	 * @param name
	 * @param value
	 */
	public Card(String rank, String suit, String name, int value)
	{
		this.rank = rank;
		this.suit = suit;
		this.name = name;
		this.value = value;
	
	}
	
	/**
	 * @return get rank
	 */
	public String getRank()
	{
		return rank;
	}
	
	/**
	 * @return get suit
	 */
	public String getSuit()
	{
		return suit;
	}
	
	/**
	 * @return get name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return get value
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * prints card name
	 */
	public void printCardName()
	{
		System.out.print(this.name);
	}
	
	/**
	 * prints card value
	 */
	public void printCardValue()
	{
		System.out.print(this.value);
	}

}
