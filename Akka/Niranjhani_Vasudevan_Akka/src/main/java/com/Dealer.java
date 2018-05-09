package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.Messages.PlayerVal;
import com.Messages.StartGame;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class Dealer extends UntypedActor {
	private static Card player1Card;
	private static Card player2Card;
	private static LinkedList<Card> player1Deck;
	private static LinkedList<Card> player2Deck;
	private static int player1Points;
	private static int player2Points;
	private static ActorRef sender = null;

	@Override
	public void onReceive(Object msg) throws Exception {
		// TODO Auto-generated method stub

		if (msg instanceof StartGame) {

			this.sender = getContext().sender();

			List<Card> cardDeck = new ArrayList<Card>();

			ActorRef game = getContext().actorOf(Props.create(Game.class), "Game");
			// ActorRef dealerNode = getContext().actorOf(Props.create(Dealer.class));

			for (int x = 0; x < 4; x++) { // 0-3 for suit (4 suits)
				for (int y = 2; y < 15; y++) { // 2-14 for rank (13 ranks)
					cardDeck.add(new Card(x, y)); // create new card and add into the deck
				} // end rank for
			}
			Collections.shuffle(cardDeck, new Random());

			game.tell(cardDeck, self());

		}

		if (msg instanceof PlayerVal) {
			PlayerVal player = (PlayerVal) msg;
			validate(player);
			Result result = null;
			if (player.getPlayerPoints1() > player.getPlayerPoints2()) {
				result = new Result(player.getPlayerName1(), player.getPlayerPoints1());
			} else {

				result = new Result(player.getPlayerName2(), player.getPlayerPoints2());

			}

			this.sender.tell(result, getSelf());

		}

	}

	private void validate(PlayerVal player) {
		int totalPoints = 0;
		int totalPointsPlayer1 = 0;
		int totalPointsPlayer2 = 0;

		List<Card> totalPointsDeck = new ArrayList<Card>();
		for (int x = 0; x < 4; x++) { // 0-3 for suit (4 suits)
			for (int y = 2; y < 15; y++) { // 2-14 for rank (13 ranks)
				totalPointsDeck.add(new Card(x, y)); // create new card and add into the deck
			} // end rank for
		}

		for (Card deckCard : totalPointsDeck) {
			totalPoints += deckCard.getCard();
		}
		for (Card p1 : player.getDeck1()) {
			totalPointsPlayer1 += p1.getCard();
		}
		for (Card p1 : player.getDeck2()) {
			totalPointsPlayer2 += p1.getCard();
		}

		if (totalPointsPlayer1 == player.getPlayerPoints1()) {

			System.out.println("Player 1 reported proper score");

		} else {
			System.out.println("Player 1 is cheating");
		}

		if (totalPoints == (player.getPlayerPoints1() + player.getPlayerPoints2())) {

			System.out.println("Player 1 points and player 2 points is equal to total points present");
		}

		if (totalPointsPlayer2 == player.getPlayerPoints2()) {

			System.out.println("Player 2 reported proper score");

		} else {
			System.out.println("Player 2 is cheating");
		}

	}

}
