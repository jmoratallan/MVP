package com.jorgemn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class TournamentSimulation {

	public static void main(String[] args) {
		
		bienvenida();
		
		//Definir jugadores base
		List<Player> players = generatePlayers(10);
	    
		//Generar partidos
	    //Se ha diseñado un generador automático de partidos con estadisticas aleatorias, intentando ser lo más coherente y realista posible.
	    List <Game> games = generateGame(players, generateNumGames());
		
		//Volcar MVP
		showMVP(games);
	}
	
	
	private static void bienvenida() {
		 System.out.println("********************************************");
		 System.out.println("¡¡¡¡¡¡¡BIENVENIDO AL TUCAN TOURNAMET!!!!!!!!");
		 System.out.println("********************************************");
		
	}
	
	private static int generateNumGames() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);  // Create a Scanner object	   
		System.out.println("¿Cuántos partidos desea que tenga el torneo?");
		return scan.nextInt();
	}

	

	private static List<Player> generatePlayers(int numPlayers) {
		
		List<Player> players = new ArrayList<Player>();
		for(int i = 1; i <= numPlayers; i++) {
			Player player = new Player(i, "Player" + i, "Nick" + i,  i+3);
			players.add(player);
		}
		
		return players;
	}
	
	private static List<Game> generateGame(List<Player> players, int numGames) {
		List<Game> games = new ArrayList<Game>();
		
		for(int i = 0; i < numGames; i++){
			Game game = new Game(new Random().nextInt(2)+1 == 1 ? "BASKETBALL" : "HANDBALL");
			game.generateGame(players);
			games.add(game);
			printGame(game, i);
		}
		
		return games;
	}

	private static void printGame(Game game, int gameNum) {
	
		System.out.println("GAME "+(gameNum+1)+"\n"+game.getSport());
		for(Player key : game.getTeamA().getPlayerStats().keySet()) {
			System.out.println(key.getName()+";"+key.getNick()+";"+key.getNumber()+";TEAM A;"+game.getTeamA().getPlayerStats().get(key).toString());
		}
		for(Player key : game.getTeamB().getPlayerStats().keySet()) {
			System.out.println(key.getName()+";"+key.getNick()+";"+key.getNumber()+";TEAM B;"+game.getTeamB().getPlayerStats().get(key).toString());
		}
		System.out.println("");
		}

	private static void showMVP(List<Game> games){
		Map<Player, Integer> playerTotalRatings = new HashMap<Player, Integer>();
		for (Game game : games) {
			Map<Player, Integer> playerRatings = game.calculetaRatings();
			for(Player player : playerRatings.keySet()) {
				if(!playerTotalRatings.containsKey(player))
					playerTotalRatings.put(player, playerRatings.get(player));
				else
					playerTotalRatings.replace(player, playerTotalRatings.get(player) + playerRatings.get(player));
			}	
		
		}
		//Buscamos el jugador con mayor rating, MVP, y lo mostramos.
		Player playerMVP = playerTotalRatings.entrySet().stream().max((player1, player2) -> player1.getValue() > player2.getValue() ? 1 : -1).get().getKey(); //Aprovechamos los recursos de Java 8 para encontrar el máximo rating del HashMap
		System.out.println("MVP del torneo: \n"+playerMVP.toString()+" \nCon un rating final de: "+playerTotalRatings.get(playerMVP).toString());//Estadisticas MVP
	}




}
