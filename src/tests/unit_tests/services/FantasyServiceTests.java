package tests.unit_tests.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Before;
import org.junit.Test;

import main.enums.Position;
import main.models.Player;
import main.models.Result;
import main.services.FantasyService;

public class FantasyServiceTests {
	
	private FantasyService fantasyService; 
	
	@Before
	public void setup() {
		Map<String, Double> stats = new HashMap<>() {{
			put("IP", 0.45);
			put("ERA", 0.5);
		}};
		List<Player> players = List.of(
				new Player("player1", "team1", List.of(Position.PITCHER), false, stats),
				new Player("player2", "team2", List.of(Position.PITCHER), false, stats)
		);
		
		fantasyService = new FantasyService(players);
	}


	@Test
	public void performODraft_success() {
		
	}
	
	@Test
	public void performODraft_failure() {
		
	}
	
	@Test
	public void performIDraft_success() {
		
	}
	
	@Test
	public void performIDraft_failure() {
		
	}
	
	@Test
	public void performOverall_success() {
		
	}
	
	@Test
	public void performPOverall_success() {

	}
	
	@Test
	public void performPOverall_failure() {
		
	}
	
	@Test
	public void performTeam_success() {
		
	}
	
	@Test
	public void performTeam_failure() {
		
	}
	
	@Test
	public void performStars_success() {
		
	}
	
	@Test
	public void performStars_failure() {
		
	}
	
	@Test
	public void performEvalFun_success() {
		
	}
	
	@Test
	public void performEvalFun_failure() {
		
	}
	
	@Test
	public void performPEvalFun_success() {
		// Arrange
		List<String> args = List.of("IP + ERA");
		
		// Act
		Result result = fantasyService.performPEvalFun(args);
		
		// Assert
		assertTrue(result.successful());
	}
	
	@Test
	public void performPEvalFun_false() {
		// Arrange
		List<String> args = List.of("SO / IP + ERA");
		
		// Act
		Result result = fantasyService.performPEvalFun(args);
		
		// Assert
		assertFalse(result.successful());
	}
	
}
