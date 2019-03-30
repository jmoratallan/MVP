package com.jorgemn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game {

	private String sport;
	private Team teamA;
	private Team teamB;
	

	public Game(String sport) {
		this.sport = sport;
		teamA = new Team();
		teamB = new Team();
	}

	public void generateGame(List<Player> players) {
		//Elegir 6 jugadores para el partido, no repetidos
		while(teamA.getPlayerStats().size()<3 || teamB.getPlayerStats().size()<3){
			
			int playerID = new Random().nextInt(players.size());
			Player player = players.get(playerID);
			String pos = "";
			
			//Si el jugador no está en ningun equipo lo añadimos
			if(!teamA.getPlayerStats().containsKey(player) && !teamB.getPlayerStats().containsKey(player)) {
				
				if(teamA.getPlayerStats().size() < 3) {
					switch(teamA.getPlayerStats().size()) {
						case 0:
							pos = "G";
							break;
						case 1:
							pos = "F";
							break;
						case 2:
							pos = sport == "BASKETBALL" ? "C" : "F";
							break;							
					}
					Stat stats =  new Stat(pos);
					teamA.getPlayerStats().put(player, stats);
					
				}
				else {
					switch(teamB.getPlayerStats().size()) {
					case 0:
						pos = "G";
						break;
					case 1:
						pos = "F";
						break;
					case 2:
						pos = sport == "BASKETBALL" ? "C" : "F";
						break;							
					}
					Stat stats =  new Stat(pos);
					teamB.getPlayerStats().put(player, stats);
				}
			}
		
		}
		/*Una vez formados los equipos generamos las stats para que sean coherentes
		  No más asistencias que puntos en Basket 
		  Los mismos goles recibidos que los anotados por el equipo contrario en Balonmano*/
		if(sport.equals("BASKETBALL"))
			generateBasketPoints();
		else
			generateHandBallGoals();
	}

	private void generateHandBallGoals() {
		int totalGoalsA = 0;
		int totalGoalsB = 0;
		int goals = 0;
		for(Player key : teamA.getPlayerStats().keySet()) {
			goals = teamA.getPlayerStats().get(key).getPosition().equals("G") ? new Random().nextInt(5) : new Random().nextInt(30);
			teamA.getPlayerStats().get(key).getStats().add(goals); //goles
			totalGoalsA += goals;
		}
		for(Player key : teamB.getPlayerStats().keySet()) {
			goals = teamB.getPlayerStats().get(key).getPosition().equals("G") ? new Random().nextInt(5) : new Random().nextInt(30);
			teamB.getPlayerStats().get(key).getStats().add(goals); //goles
			totalGoalsB += goals;
		}
		//Ya están generados los puntos y goles de los partidos, 
		//vamos a calcular el ganador para ahorranos el paso en el calculo de ratings
		if(totalGoalsA >= totalGoalsB) //Simulamos que si empatan a puntos gana el equipo Local, por poner algun criterio.
			teamA.setWinner(true);
		else
			teamB.setWinner(true);
		generateHandBallGoalsReceived(totalGoalsA, totalGoalsB);
		
	}

	private void generateHandBallGoalsReceived(int totalGoalsA, int totalGoalsB) {
		for(Player key : teamA.getPlayerStats().keySet()) {
			teamA.getPlayerStats().get(key).getStats().add(totalGoalsB); //Goles recibidos
		}
		for(Player key : teamB.getPlayerStats().keySet()) {
			teamB.getPlayerStats().get(key).getStats().add(totalGoalsA); //Goles recibidos
		}
		
	}

	public void generateBasketPoints() {
		int totalPointsA = 0;
		int totalPointsB = 0;
		int points = 0;
		for(Player key : teamA.getPlayerStats().keySet()) {
			points = new Random().nextInt(30);
			teamA.getPlayerStats().get(key).getStats().add(points); //Puntos Anotados
			teamA.getPlayerStats().get(key).getStats().add(new Random().nextInt(15)); //Rebotes
			totalPointsA += points;
		}
		for(Player key : teamB.getPlayerStats().keySet()) {
			points = new Random().nextInt(30);
			teamB.getPlayerStats().get(key).getStats().add(points); //Puntos Anotados
			teamB.getPlayerStats().get(key).getStats().add(new Random().nextInt(15)); //Rebotes
			totalPointsB += points;
		}
		//Ya están generados los puntos y goles de los partidos, 
		//vamos a calcular el ganador para ahorranos el paso en el calculo de ratings
		if(totalPointsA >= totalPointsB) //Simulamos que si empatan a puntos gana el equipo Local, por poner algun criterio.
			teamA.setWinner(true);
		else
			teamB.setWinner(true);
		generateBasketAsists(totalPointsA, totalPointsB);
		
	}
	
	private void generateBasketAsists(int totalA, int totalB) {
		//Solo los puntos de 2 o 3 pueden tener asist, los tiros libres (1) no. Asi que como mucho puede haber la mitad de asistencias que puntos.
		int maxAsistsA = totalA/2;
		int maxAsistsB = totalB/2;
		int asists = 0;
		for(Player key : teamA.getPlayerStats().keySet()) {
			asists = new Random().nextInt(maxAsistsA);
			teamA.getPlayerStats().get(key).getStats().add(asists); //Asistencias
			maxAsistsA -= asists;
		}
		for(Player key : teamB.getPlayerStats().keySet()) {
			asists = new Random().nextInt(maxAsistsB);
			teamB.getPlayerStats().get(key).getStats().add(asists); //Asistencias
			maxAsistsB -= asists;
		}
		
	}

	public Map<Player, Integer> calculetaRatings() {
		Map<Player, Integer> playerPoints = new HashMap<Player, Integer>();
		if(sport.equals("BASKETBALL")) {
			for(Player key : teamA.getPlayerStats().keySet()) {
				int totalPlayerRating =	teamA.getWinner() ? 10 : 0; //Los jugadores ganadores empiezan con 10 puntos extra de rating
				totalPlayerRating += 2 * teamA.getPlayerStats().get(key).getStats().get(0);//Puntos
				switch(teamA.getPlayerStats().get(key).getPosition()){//Calculamos rebotes y asists por posicion
				case "G":
					totalPlayerRating += 3 * teamA.getPlayerStats().get(key).getStats().get(1);//Rebotes
					totalPlayerRating += 1 * teamA.getPlayerStats().get(key).getStats().get(2);//Asistencias
					break;
				case "F":
					totalPlayerRating += 2 * teamA.getPlayerStats().get(key).getStats().get(1);//Rebotes
					totalPlayerRating += 2 * teamA.getPlayerStats().get(key).getStats().get(2);//Asistencias
					break;
				case "C":
					totalPlayerRating += 1 * teamA.getPlayerStats().get(key).getStats().get(1);//Rebotes
					totalPlayerRating += 3 * teamA.getPlayerStats().get(key).getStats().get(2);//Asistencias
					break;
				}
				playerPoints.put(key, totalPlayerRating);//Insertamos el jugador del partido con su rating
			}
			for(Player key : teamB.getPlayerStats().keySet()) {
				int totalPlayerRating =	teamB.getWinner() ? 10 : 0; //Los jugadores ganadores empiezan con 10 puntos extra de rating
				totalPlayerRating += 2 * teamB.getPlayerStats().get(key).getStats().get(0);//Puntos
				switch(teamB.getPlayerStats().get(key).getPosition()){//Calculamos rebotes y asists por posicion
				case "G":
					totalPlayerRating += 3 * teamB.getPlayerStats().get(key).getStats().get(1);//Rebotes
					totalPlayerRating += 1 * teamB.getPlayerStats().get(key).getStats().get(2);//Asistencias
					break;
				case "F":
					totalPlayerRating += 2 * teamB.getPlayerStats().get(key).getStats().get(1);//Rebotes
					totalPlayerRating += 2 * teamB.getPlayerStats().get(key).getStats().get(2);//Asistencias
					break;
				case "C":
					totalPlayerRating += 1 * teamB.getPlayerStats().get(key).getStats().get(1);//Rebotes
					totalPlayerRating += 3 * teamB.getPlayerStats().get(key).getStats().get(2);//Asistencias
					break;
				}
				playerPoints.put(key, totalPlayerRating);//Insertamos el jugador del partido con su rating
			}
		}
		else {//HANDBALL
			for(Player key : teamA.getPlayerStats().keySet()) { //Estadisticas teamA
				int totalPlayerRating =	teamA.getWinner() ? 10 : 0;//Los jugadores ganadores empiezan con 10 puntos extra de rating
				switch(teamA.getPlayerStats().get(key).getPosition()){//Calculamos rebotes y asists por posicion
				case "G":
					totalPlayerRating += 50;
					totalPlayerRating += 5 * teamA.getPlayerStats().get(key).getStats().get(0);//Goles
					totalPlayerRating += -2 * teamA.getPlayerStats().get(key).getStats().get(1);//Goles recibidos
					break;
				case "F":
					totalPlayerRating += 20;
					totalPlayerRating += 1 * teamA.getPlayerStats().get(key).getStats().get(0);//Goles
					totalPlayerRating += -1 * teamA.getPlayerStats().get(key).getStats().get(1);//Goles recibidos
					break;
				}
				playerPoints.put(key, totalPlayerRating);//Insertamos el jugador del partido con su rating
			}
			for(Player key : teamB.getPlayerStats().keySet()) { //Estadísticas teamB
				int totalPlayerRating =	teamB.getWinner() ? 10 : 0;//Los jugadores ganadores empiezan con 10 puntos extra de rating
				switch(teamB.getPlayerStats().get(key).getPosition()){//Calculamos rebotes y asists por posicion
				case "G":
					totalPlayerRating += 50;
					totalPlayerRating += 5 * teamB.getPlayerStats().get(key).getStats().get(0);//Goles
					totalPlayerRating += -2 * teamB.getPlayerStats().get(key).getStats().get(1);//Goles recibidos
					break;
				case "F":
					totalPlayerRating += 20;
					totalPlayerRating += 1 * teamB.getPlayerStats().get(key).getStats().get(0);//Goles
					totalPlayerRating += -1 * teamB.getPlayerStats().get(key).getStats().get(1);//Goles recibidos
					break;
				}
				playerPoints.put(key, totalPlayerRating);//Insertamos el jugador del partido con su rating
			}
		}
		return playerPoints;
	}
	
	public String getSport() {
		return sport;
	}
	
	public Team getTeamA() {
		return teamA;
	}

	public Team getTeamB() {
		return teamB;
	}

	
}
