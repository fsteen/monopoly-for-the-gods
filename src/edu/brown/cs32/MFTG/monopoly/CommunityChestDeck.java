package edu.brown.cs32.MFTG.monopoly;

import java.util.Collections;
import java.util.LinkedList;
public class CommunityChestDeck {
	private LinkedList<Card> _deck;
	
	/**
	 * constructs a new deck
	 */
	public CommunityChestDeck() {
		_deck = new LinkedList<Card>();
		_deck.add(new AdvanceToSpaceCard("go"));
		_deck.add(new GainMoneyCard(200));
		_deck.add(new LoseMoneyCard(50));
		_deck.add(new GainMoneyCard(50));
		_deck.add(new GetOutOfJailFreeCard());
		_deck.add(new GoToJailCard());
		_deck.add(new GainMoneyFromEachPlayerCard(50));
		_deck.add(new GainMoneyCard(100));
		_deck.add(new GainMoneyCard(20));
		_deck.add(new GainMoneyFromEachPlayerCard(10));
		_deck.add(new GainMoneyCard(100));
		_deck.add(new LoseMoneyCard(100));
		_deck.add(new LoseMoneyCard(150));
		_deck.add(new GainMoneyCard(25));
		_deck.add(new PayPerHouseCard(40,115));
		_deck.add(new GainMoneyCard(10));
		_deck.add(new GainMoneyCard(100));
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
