package main;

import java.util.List;

import main.enums.Action;
import main.models.Player;
import main.models.Result;
import main.models.UserAction;
import main.services.FantasyService;
import main.utils.FileUtils;
import main.view.View;

public class FantasyDriver {
	
	private static String rootDirectory = System.getProperty("user.dir") + "/files";
	private static String battingStatsFileName = "mlb_al_batter_stats_2023.txt";
	private static String pitchingStatsFileName = "mlb_al_pitching_stats_2023.txt";
	private static FantasyService fantasyService;

	public static void main(String[] args) {
		List<Player> players = getPlayers();
		if (players == null) {return;}
		
		fantasyService = new FantasyService(players);
		
		while (true) {
			UserAction userAction = View.promptForAction();
			
			if (userAction.getAction() == Action.QUIT) {
				break;
			}
			
			Result result = performAction(userAction);
			
			if (result != null && result.getMessage() != null) {
				View.displayMessage(result.getMessage());
			}
		}
	}
	
	private static List<Player> getPlayers() {
		try {
			return FileUtils.readInPlayers(rootDirectory, battingStatsFileName, pitchingStatsFileName);
		} catch (Exception ex) {
			System.out.println("An error occurred while reading in stats. Please ensure the following files are in the 'files' folder:");
			System.out.println("- " + battingStatsFileName);
			System.out.println("- " + pitchingStatsFileName);
			return null;
		}
	}

	private static Result performAction(UserAction userAction) {
		switch (userAction.getAction()) {
			case ODRAFT:
				return fantasyService.performODraft(userAction.getArgs());
			case IDRAFT:
				return fantasyService.performIDraft(userAction.getArgs());
			case OVERALL:
				return fantasyService.performOverall(userAction.getArgs());
			case POVERALL:
				return fantasyService.performPOverall(userAction.getArgs());
			case TEAM:
				return fantasyService.performTeam(userAction.getArgs());
			case STARS:
				return fantasyService.performStars(userAction.getArgs());
			case SAVE:
				return FileUtils.save(userAction.getArgs(), rootDirectory, fantasyService);
			case RESTORE:
				return FileUtils.restore(userAction.getArgs(), rootDirectory, fantasyService);
			case EVALFUN:
				return fantasyService.performEvalFun(userAction.getArgs());
			case PEVALFUN:
				return fantasyService.performPEvalFun(userAction.getArgs());
			case WEIGHT:
				return fantasyService.performWeight(userAction.getArgs());
			case HELP:
				return View.displayHelp();
			default:
				System.out.println(userAction.getAction() + " not implemented.");
				return null;
		}
	}

}
