package edu.brown.cs32.MFTG.monopoly;

import java.util.Collections;
import java.util.LinkedList;
public class CommunityChestDeck implements Deck{
	private LinkedList<Card> _deck;
	
	/**
	 * constructs a new deck
	 */
	public CommunityChestDeck() {
		_deck = new LinkedList<Card>();
		_deck.add(new AdvanceToSpaceCard("go",this));
		_deck.add(new GainMoneyCard(200,this));
		_deck.add(new LoseMoneyCard(50,this));
		_deck.add(new GainMoneyCard(50,this));
		_deck.add(new GetOutOfJailFreeCard(this));
		_deck.add(new GoToJailCard(this));
		_deck.add(new GainMoneyFromEachPlayerCard(50,this));
		_deck.add(new GainMoneyCard(100,this));
		_deck.add(new GainMoneyCard(20,this));
		_deck.add(new GainMoneyFromEachPlayerCard(10,this));
		_deck.add(new GainMoneyCard(100,this));
		_deck.add(new LoseMoneyCard(100,this));
		_deck.add(new LoseMoneyCard(150,this));
		_deck.add(new GainMoneyCard(25,this));
		_deck.add(new PayPerHouseCard(40,115,this));
		_deck.add(new GainMoneyCard(10,this));
		_deck.add(new GainMoneyCard(100,this));
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
