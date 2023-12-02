package main.view;

import main.enums.Action;
import main.models.Result;
import main.models.UserAction;
import java.util.*;

public class View {

	public static UserAction promptForAction() {
		System.out.print("$ ");
		Scanner keyboard = new Scanner(System.in);
		String input = keyboard.nextLine();
		Action userActionInput = null;
		
		// Split it to be "action","argument" if there is an argument
		if (input.contains(" ")) {
			String splitInput[] = input.split(" ",2);
		
			userActionInput = getAction(splitInput[0]);
		
			// Store argument
			List<String> args = new ArrayList<String>();
			args.add(splitInput[1]);
			
			return new UserAction(userActionInput, args);
		}
		
		// Just an action
		else {
			userActionInput = getAction(input);
			return new UserAction(userActionInput, null);
		}
	}

	public static Result displayHelp() {
		System.out.println("\n●・○・●・○・●・○・●・○・●・○・●・○・●・○・●・○");
		System.out.println("Here are possible commands:\n︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵‿︵");
		System.out.println("°。 ODRAFT \"playerName\" leagueMember 。°\n"
				+ "°。 IDRAFT \"playerName\" 。°\n"
				+ "°。 OVERALL position 。°\n"
				+ "°。 POVERALL 。°\n"
				+ "°。 TEAM leagueMember 。°\n"
				+ "°。 STARS leagueMember 。°\n"
				+ "°。 SAVE fileName 。°\n"
				+ "°。 QUIT 。°\n"
				+ "°。 RESTORE fileName 。°\n"
				+ "°。 EVALFFUN expression 。°\n"
				+ "°。 PEVALFUN expression 。°\n"
				+ "°。 HELP 。°\n");
		
		return new Result(true, null);
	}

	public static void displayMessage(String message) {
		System.out.println(message);
	}
	
	public static Action getAction(String userAction) {
		String userActionCaps = userAction.toUpperCase();
		Action action;
		switch(userActionCaps) {
		case "ODRAFT":
			action = Action.ODRAFT;
			break;
		case "IDRAFT":
			action = Action.IDRAFT;
			break;
		case "OVERALL":
			action = Action.OVERALL;
			break;
		case "POVERALL":
			action = Action.POVERALL;
			break;
		case "TEAM":
			action = Action.TEAM;
			break;
		case "STARS":
			action = Action.STARS;
			break;
		case "SAVE":
			action = Action.SAVE;
			break;
		case "RESTORE":
			action = Action.RESTORE;
			break;
		case "EVALFUN":
			action = Action.EVALFUN;
			break;
		case "PEVALFUN":
			action = Action.PEVALFUN;
			break;
		case "WEIGHT":
			action = Action.WEIGHT;
			break;
		case "HELP":
			action = Action.HELP;
			break;
		case "QUIT":
			action = Action.QUIT;
			break;
		default:
			action = Action.QUIT;
			break;
		}
		
		return action;
	}
	
}
