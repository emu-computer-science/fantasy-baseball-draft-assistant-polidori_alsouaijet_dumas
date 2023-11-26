package main.models;

import java.util.List;
import java.util.Map;

import main.enums.Position;

public class Player {

	private String name;
	private String team;
	private List<Position> positions;
	private boolean drafted;
	private Map<String, Double> stats;
	
	public Player() {
	}
	
	public Player(String name, String team, List<Position> positions, boolean drafted, Map<String, Double> stats) {
		super();
		this.name = name;
		this.team = team;
		this.positions = positions;
		this.drafted = drafted;
		this.stats = stats;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public boolean isDrafted() {
		return drafted;
	}

	public void setDrafted(boolean drafted) {
		this.drafted = drafted;
	}

	public Map<String, Double> getStats() {
		return stats;
	}

	public void setStats(Map<String, Double> stats) {
		this.stats = stats;
	}
	
}
