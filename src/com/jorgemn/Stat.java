package com.jorgemn;

import java.util.ArrayList;
import java.util.List;

public class Stat {


	private String position;
	private List<Integer> stats;
	
	

	public Stat(String pos) {
		this.position = pos;
		stats =  new ArrayList<Integer>();
	}

	public List<Integer> getStats() {
		return stats;
	}

	public void setStats(List<Integer> stats) {
		this.stats = stats;
	}


	public String getPosition() {
		return position;
	}

	@Override
	public String toString() {
		String stream = position + ";";
		for (Integer stat : stats) {
			stream =  stream + stat + ";";
		}
		stream.concat("\n");
		return stream;
	}


	
	
	
}
