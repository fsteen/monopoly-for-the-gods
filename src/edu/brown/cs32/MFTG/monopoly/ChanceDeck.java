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
		_deck.add(new AdvanceToSpaceCard("go"));
		_deck.add(new AdvanceToSpaceCard("illinois avenue"));
		_deck.add(new AdvanceToSpaceCard("st. charles place"));
		_deck.add(new AdvanceToNearestUtilityCard());
		_deck.add(new AdvanceToNearestRailroadCard());
		_deck.add(new GainMoneyCard(50));
		_deck.add(new GetOutOfJailFreeCard());
		_deck.add(new GoBackThreeSpacesCard());
		_deck.add(new GoToJailCard());
		_deck.add(new PayPerHouseCard(25,100));
		_deck.add(new LoseMoneyCard(15));
		_deck.add(new AdvanceToSpaceCard("reading railroad"));
		_deck.add(new AdvanceToSpaceCard("boardwalk"));
		_deck.add(new LoseMoneyToEachPlayerCard(50));
		_deck.add(new GainMoneyCard(150));
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
