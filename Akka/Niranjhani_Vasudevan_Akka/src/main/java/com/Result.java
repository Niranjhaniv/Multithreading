package com;

public class Result {

	private String playerName;
	private int playerPoints;

	public Result(String playerName, int playerPoints) {
		this.playerName = playerName;
		this.playerPoints = playerPoints;
	}

	public String toString() {
		return "Player name : " + playerName + " " + "Points :" + playerPoints + " is the winner";
	}

}