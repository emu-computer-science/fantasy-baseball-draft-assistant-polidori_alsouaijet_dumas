package main.services;

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
	private List<Player> draftA = new ArrayList<>();
	private List<Player> draftB =  new ArrayList<>();
	private List<Player> draftC = new ArrayList<>();
	private List<Player> draftD = new ArrayList<>();
	
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
		// Split args
		String argsSplit[] = args.get(0).split("\"");
		String playerName = argsSplit[1];
		String leagueMember = argsSplit[2].substring(1,2).toUpperCase();
		
		int index = -1;
		
		// Go through the players list to find the playerName
		for (int i = 0; i < players.size(); i++) {
			String listPlayerSplit[] = players.get(i).getName().split(" ");
			String listPlayerFirstInit = listPlayerSplit[0].substring(0,1);
			String listPlayerLast = listPlayerSplit[1];
			
			// Check for first + last name 
			if (playerName.contains(", ")) {
				String playerNameSplit[] = playerName.split(",");
				String playerNameLast = playerNameSplit[0];
				String playerNameFirstInit = playerNameSplit[1].replace(" ", "");
				if (playerNameLast.equalsIgnoreCase(listPlayerLast) && playerNameFirstInit.equalsIgnoreCase(listPlayerFirstInit))
					index = i;
			}
			// Check just last name
			else
				if (playerName.equalsIgnoreCase(listPlayerLast)) {
					if (index != -1) 
						return new Result(false, "there are multiple players with that last name, be more specific");
				index = i;
			}
		}
		
		// If index = -1, then the player wasn't found
		if (index == -1) 
			return new Result(false, "no player was drafted");

		// Add the found player to their leagueMember's draft if they're not drafted yet
		// Hash this later?
		if (players.get(index).isDrafted())
			return new Result(false, "this player was already drafted");
		else {
			switch (leagueMember) {
			case "A":
				draftA.add(players.get(index));
				players.get(index).setDrafted(true);
				break;
			case "B":
				draftB.add(players.get(index));
				players.get(index).setDrafted(true);
				break;
			case "C":
				draftC.add(players.get(index));
				players.get(index).setDrafted(true);
				break;
			case "D":
				draftD.add(players.get(index));
				players.get(index).setDrafted(true);
				break;
			default:
				return new Result(false, "not a valid league member, no player was drafted");
			}
		}
		
		return null;
	}

	public Result performIDraft(List<String> args) {
		// Split args
		String argsSplit[] = args.get(0).split("\"");
		String playerName = argsSplit[1];
		
		int index = -1;
		
		// Go through the players list to find the playerName
		for (int i = 0; i < players.size(); i++) {
			String listPlayerSplit[] = players.get(i).getName().split(" ");
			String listPlayerFirstInit = listPlayerSplit[0].substring(0,1);
			String listPlayerLast = listPlayerSplit[1];
			
			// Check for first + last name 
			if (playerName.contains(", ")) {
				String playerNameSplit[] = playerName.split(",");
				String playerNameLast = playerNameSplit[0];
				String playerNameFirstInit = playerNameSplit[1].replace(" ", "");
				if (playerNameLast.equalsIgnoreCase(listPlayerLast) && playerNameFirstInit.equalsIgnoreCase(listPlayerFirstInit))
					index = i;
			}
			// Check just last name
			else
				if (playerName.equalsIgnoreCase(listPlayerLast)) {
					if (index != -1) 
						return new Result(false, "there are multiple players with that last name, be more specific");
				index = i;
			}
		}
		
		// If index = -1, then the player wasn't found
		if (index == -1) 
			return new Result(false, "no player was drafted");

		// Add the found player to member A's draft if they're not drafted yet
		if (players.get(index).isDrafted())
			return new Result(false, "this player was already drafted");
		else {
			draftA.add(players.get(index));
		}
		
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
		String expression = args.get(0).toLowerCase();
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
