package edu.brown.cs32.MFTG.monopoly;

import java.util.Collections;
import java.util.LinkedList;
public class ChanceDeck {
	private LinkedList<Card> _deck;
	
	/**
	 * constructs a new deck
	 */
	public ChanceDeck() {
		_deck = new LinkedList<Card>();
		Collections.shuffle(_deck);		
	}

	/**
	 * 
	 * @return card on top of the deck
	 */
	public Card drawCard(){
		return _deck.poll();
	}
	
	/**
	 * adds newCard to bottom of deck
	 * @param newCard
	 */
	public void putCardOnBottom(Card newCard){
		_deck.offer(newCard);
	}
	
	

}
