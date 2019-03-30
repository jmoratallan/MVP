package com.jorgemn;

import java.util.HashMap;
import java.util.Map;

public class Team {

	private Boolean winner;
	private Map<Player, Stat> playerStats = new HashMap<Player, Stat>();
	
	public Team() {
		winner = false; //Generamos derrota por defecto y al ganador le setearemos true
	}

	public Map<Player, Stat> getPlayerStats() {
		return playerStats;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
		
	}

	public Boolean getWinner() {
		return winner;
	}
	
	
	
	
}
