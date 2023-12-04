package tests.unit_tests.services;

import static org.junit.Assert.assertEquals;
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
				new Player("player1", "team1", (Position.PITCHER), false, stats),
				new Player("player2", "team2", (Position.PITCHER), false, stats)
		);
		
		fantasyService = new FantasyService(players);
	}


	/*@Test
	public void performODraft_success() {
		// Arrange
		List<String> args = List.of(" \"Abreu, J\" B");
		
		// Act
		Result result = fantasyService.performODraft(args);
		
		// Assert
		assertTrue(result.successful());
	}
	
	@Test
	public void performODraft_nonExistentPlayer_false() {
		// Arrange
		List<String> args = List.of(" \"Polidori, S\" B");
		
		// Act
		Result result = fantasyService.performODraft(args);
		
		// Assert
		assertFalse(result.successful());
		assertEquals(result.getMessage(), "no player was drafted");
	}
	
	@Test
	public void performODraft_nonExistentLeagueMember_false() {
		// Arrange
		List<String> args = List.of(" \"Abreu, J\" F");
		
		// Act
		Result result = fantasyService.performODraft(args);
		
		// Assert
		assertFalse(result.successful());
		assertEquals(result.getMessage(), "not a valid league member, no player was drafted");
	}
	*/
	
	@Test
	public void performODraft_noLeagueMemberInArgs_false() {
		// ☆○o Arrange ☆○o
		List<String> args = List.of(" \"Abreu, J\"");
		
		// Act
		Result result = fantasyService.performODraft(args);
		
		// Assert
		assertFalse(result.successful());
		assertEquals(result.getMessage(), "Please enter a league member");
	}
	
	@Test
	public void performODraft_noArgs_false() {
		// Act 
		Result result = fantasyService.performODraft(null);
		
		// Assert 
		assertFalse(result.successful());
		assertEquals(result.getMessage(), "Please enter a player and league member");
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
	public void performPEvalFun_nonExistantStats_false() {
		// Arrange
		List<String> args = List.of("SO / IP + ERA");
		
		// Act
		Result result = fantasyService.performPEvalFun(args);
		
		// Assert
		assertFalse(result.successful());
		assertEquals(result.getMessage(), "'so' is not a valid statistic.");
	}
	
	@Test
	public void performPEvalFun_nullArgs_false() {
		// Act
		Result result = fantasyService.performPEvalFun(null);
		
		// Assert
		assertFalse(result.successful());
		assertEquals(result.getMessage(), "Please enter an expression");
	}
}
