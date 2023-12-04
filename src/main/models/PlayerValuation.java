package main.models;

public class PlayerValuation {
	
	private Player player;
	private Double valuation;
	
	public PlayerValuation(Player player, Double valuation) {
		this.player = player;
		this.valuation = valuation;
	}

	public Player getPlayer() {
		return player;
	}

	public Double getValuation() {
		return valuation;
	}
	
}