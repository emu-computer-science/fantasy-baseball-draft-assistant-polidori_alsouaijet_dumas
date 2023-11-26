package main.models;

import java.util.List;

import main.enums.Action;

public class UserAction {

	private final Action action;
	private final List<String> args;
	
	public UserAction(Action action, List<String> args) {
		this.action = action;
		this.args = args;
	}

	public Action getAction() {
		return action;
	}

	public List<String> getArgs() {
		return args;
	}
	
}
