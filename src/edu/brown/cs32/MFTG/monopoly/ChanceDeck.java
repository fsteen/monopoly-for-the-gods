package edu.brown.cs32.MFTG.monopoly;

import java.util.Collections;
import java.util.LinkedList;
public class ChanceDeck implements Deck{
	private LinkedList<Card> _deck;
	
	/**
	 * constructs a new deck
	 */
	public ChanceDeck() {
		_deck = new LinkedList<Card>();
		_deck.add(new AdvanceToSpaceCard("go", this));
		_deck.add(new AdvanceToSpaceCard("illinois avenue", this));
		_deck.add(new AdvanceToSpaceCard("st. charles place", this));
		_deck.add(new AdvanceToNearestUtilityCard(this));
		_deck.add(new AdvanceToNearestRailroadCard(this));
		_deck.add(new GainMoneyCard(50, this));
		_deck.add(new GetOutOfJailFreeCard(this));
		_deck.add(new GoBackThreeSpacesCard(this));
		_deck.add(new GoToJailCard(this));
		_deck.add(new PayPerHouseCard(25,100, this));
		_deck.add(new LoseMoneyCard(15, this));
		_deck.add(new AdvanceToSpaceCard("reading railroad", this));
		_deck.add(new AdvanceToSpaceCard("boardwalk", this));
		_deck.add(new LoseMoneyToEachPlayerCard(50, this));
		_deck.add(new GainMoneyCard(150, this));
		_deck.add(new GainMoneyCard(100, this));
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
