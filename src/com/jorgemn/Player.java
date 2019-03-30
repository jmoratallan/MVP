package com.jorgemn;


public class Player {
	
	private int id;
	private String name;
	private String nick;
	private int number;

	public Player(int id, String name, String nick, int number) {
		this.id = id;
		this.name = name;
		this.nick = nick;
		this.number = number;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Jugador: "+name+"\nApodo: "+nick+"\nNº: "+number+"";
	}
}
