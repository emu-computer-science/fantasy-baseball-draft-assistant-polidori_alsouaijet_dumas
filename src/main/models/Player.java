package main.models;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(drafted, name, positions, stats, team);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return drafted == other.drafted && Objects.equals(name, other.name)
				&& Objects.equals(positions, other.positions) && Objects.equals(stats, other.stats)
				&& Objects.equals(team, other.team);
	}

}
