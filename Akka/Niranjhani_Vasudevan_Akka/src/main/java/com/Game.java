package com;

import java.util.LinkedList;
import java.util.List;

import com.Messages.PlayerVal;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Game extends UntypedActor {

	private static ActorRef deckSender = null;

	@Override
	public void onReceive(Object deck) throws Exception {

		List<Card> cardDeck = (List<Card>) deck;
		this.deckSender = getSender();

		LinkedList<Card> deck1 = new LinkedList<Card>();
		LinkedList<Card> deck2 = new LinkedList<Card>();

		int player1Points = 0;
		int player2Points = 0;
		deck1.addAll(cardDeck.subList(0, 26)); // 26 cards for p1
		deck2.addAll(cardDeck.subList(26, cardDeck.size()));// 26 cards for p2

		for (int i = 0; i < cardDeck.size() / 2; i++) {
			Card p1Card = deck1.pop(); // each player place one card face up
			Card p2Card = deck2.pop();

			// display the face up card
			System.out.println("Player 1 plays card is " + p1Card.toString());
			System.out.println("Player 2 plays card is " + p2Card.toString());

			// rank comparation between two cards
			if (p1Card.getCard() > p2Card.getCard()) {// if player 1 win
				deck1.addLast(p1Card); // higher rank wins both cards and
				deck1.addLast(p2Card); // places them at the bottom of his deck.
				System.out.println("PLayer 1 wins the round");
				System.out.println("*******************************");
				// player1Points++;

			} // end if

			else if (p1Card.getCard() < p2Card.getCard()) {// if player 2 win
				deck2.addLast(p1Card);
				deck2.addLast(p2Card);
				System.out.println("PLayer 2 wins the round");
				System.out.println("*******************************");
				// player2Points++;

			} // end else if

			else { // war happens when both cards' rank matched
				System.out.println("War");
				System.out.println("Player 1 Card" + p1Card.getCard());
				System.out.println("Player 2 Card" + p2Card.getCard());
				System.out.println("Comapring the suit");
				int p1Points = p1Card.getCard() + p1Card.getSuit();
				int p2Points = p2Card.getCard() + p2Card.getSuit();

				System.out.println("Player 1 Card+Suit " + p1Points);
				System.out.println("Player 2 Card+suit " + p2Points);

				if (p1Points > p2Points) {// if player 1 win
					deck1.addLast(p1Card); // higher rank wins both cards and
					deck1.addLast(p2Card); // places them at the bottom of his deck.
					System.out.println("PLayer 1 wins the round");
					System.out.println("*******************************");
					// player1Points++;

				} // end if

				else if (p1Points < p2Points) {// if player 2 win
					deck2.addLast(p1Card);
					deck2.addLast(p2Card);
					System.out.println("PLayer 2 wins the round");
					System.out.println("*******************************");
					// player2Points++;

				}

			} // end war round else

		}

		for (Card card : deck1) {
			player1Points += card.getCard();

		}
		for (Card card : deck2) {
			player2Points += card.getCard();

		}
		System.out.println("Player 1 points:" + player1Points);
		System.out.println("Player 2 points:" + player2Points);

		deckSender.tell(new PlayerVal("Player 1", player1Points, "Player 2", player2Points, deck1, deck2), getSelf());

	}

}
