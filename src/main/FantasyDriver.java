package main;

import main.enums.Action;
import main.models.Result;
import main.models.UserAction;
import main.services.FantasyService;
import main.view.View;

public class FantasyDriver {
	
	private static FantasyService fantasyService = new FantasyService();

	public static void main(String[] args) {
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
				return fantasyService.performSave(userAction.getArgs());
			case RESTORE:
				return fantasyService.performRestore(userAction.getArgs());
			case EVALFUN:
				return fantasyService.performEvalFun(userAction.getArgs());
			case PEVALFUN:
				return fantasyService.performPEvalFun(userAction.getArgs());
			case HELP:
				return View.displayHelp();
			default:
				System.out.println(userAction.getAction() + " not implemented.");
				return null;
		}
	}

}
