package main.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import main.enums.Position;
import main.models.Player;
import main.models.Result;
import main.utils.Evaluator;
import main.utils.TypeUtils;

public class FantasyService {
	
	private final List<Player> players;
	private final Set<String> pitcherStats;
	private Evaluator pitcherEvaluator = new Evaluator("IP");
	
	public FantasyService(List<Player> players) {
		this.players = players;
		pitcherStats = getPitcherStats(players);
	}

	private Set<String> getPitcherStats(List<Player> players) {
		return players.stream()
					  .filter(player -> player.getPositions().contains(Position.PITCHER))
					  .flatMap(player -> player.getStats().keySet().stream())
					  .map(stat -> stat.toLowerCase())
					  .distinct()
					  .collect(Collectors.toSet());
	}

	public Result performODraft(List<String> args) {
		return null;
	}

	public Result performIDraft(List<String> args) {
		return null;
	}

	public Result performOverall(List<String> args) {
		return null;
	}

	public Result performPOverall(List<String> args) {
		List<PlayerValuation> playerValuations = players.stream()
													 	.filter(player -> player.getPositions().contains(Position.PITCHER) && !player.isDrafted())
													 	.map(player -> new PlayerValuation(player, pitcherEvaluator.evaluate(player)))
													 	.filter(playerValuation -> playerValuation.getValuation() != null)
													 	.sorted((p1, p2) -> -1 * Double.compare(p1.getValuation(), p2.getValuation()))
													 	.toList();
		
		String message = generatePOverallMessage(playerValuations);
		
		return new Result(true, message);
	}

	private String generatePOverallMessage(List<PlayerValuation> playerValuations) {
		StringBuilder message = new StringBuilder();
		
		for (PlayerValuation playerValuation : playerValuations) {
			Player player = playerValuation.getPlayer();
			
			message.append(player.getName());
			message.append("\t\t");
			message.append(player.getTeam());
			message.append("\t");
			message.append("P");
			message.append("\t");
			message.append(playerValuation.getValuation());
			message.append("\n");
		}
		
		return message.toString();
	}

	public Result performTeam(List<String> args) {
		return null;
	}

	public Result performStars(List<String> args) {
		return null;
	}

	public Result performSave(List<String> args) {
		return null;
	}

	public Result performRestore(List<String> args) {
		return null;
	}

	public Result performEvalFun(List<String> args) {
		return null;
	}

	public Result performPEvalFun(List<String> args) {
		String expression = "SO / IP + ERA".toLowerCase();
		String[] components = expression.split(" ");

		for (String component : components) {
			if (!TypeUtils.isOperator(component) && !TypeUtils.isNumber(component) && !pitcherStats.contains(component)) {
				return new Result(false, component + " is not a valid statistic.");
			}
		}
		
		pitcherEvaluator.setExpression(expression);
		
		return new Result(true, null);
	}
	
	class PlayerValuation {
		
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
	
}
