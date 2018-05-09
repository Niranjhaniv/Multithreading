package com;

import java.util.List;

public class Messages {

	public static class StartGame {

	}

	public static class PlayerVal {

		final private int playerPoints1;
		final private String playerName1;

		final private int playerPoints2;
		final private String playerName2;
		final private List<Card> deck1;
		final private List<Card> deck2;

		public PlayerVal(String playerName1, int playerPoints1, String playerName2, int playerPoints2, List<Card> deck1,
				List<Card> deck2) {
			this.playerName1 = playerName1;
			this.playerPoints1 = playerPoints1;
			this.playerName2 = playerName2;
			this.playerPoints2 = playerPoints2;
			this.deck1 = deck1;
			this.deck2 = deck2;
		}

		public List<Card> getDeck1() {
			return deck1;
		}

		public List<Card> getDeck2() {
			return deck2;
		}

		public int getPlayerPoints1() {
			return playerPoints1;
		}

		public String getPlayerName1() {
			return playerName1;
		}

		public int getPlayerPoints2() {
			return playerPoints2;
		}

		public String getPlayerName2() {
			return playerName2;
		}

	}

}
