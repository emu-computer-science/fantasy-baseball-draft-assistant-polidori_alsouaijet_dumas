package main.view;

import main.enums.Action;
import main.models.Result;
import main.models.UserAction;

public class View {

	public static UserAction promptForAction() {
		// TODO Auto-generated method stub
		
		return new UserAction(Action.QUIT, null);
	}

	public static Result displayHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void displayMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	
}
