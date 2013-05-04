package edu.brown.cs32.MFTG.monopoly;

/**
 * models a deck of cards
 * @author JudahSchvimer
 *
 */
public interface Deck {
	/**
	 * 
	 * @return top card of deck
	 */
	public Card drawCard();
	
	/**
	 * places card back on bottom of deck
	 * @param newCard
	 */
	public void putCardOnBottom(Card newCard);
	
}
