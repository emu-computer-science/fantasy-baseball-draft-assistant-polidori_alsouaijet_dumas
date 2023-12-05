package tests.unit_tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import main.enums.Position;
import main.models.Player;
import main.models.PlayerValuation;
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
				new Player("player 1", "team1", (Position.PITCHER), false, stats),
				new Player("Jose Abreu", "team2", (Position.PITCHER), false, stats)
		);
		
		fantasyService = new FantasyService(players);
	}


	@Test
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
		// Arrange
		List<Player> players = getPOverallTestPlayers();
		List<Player> expectedPlayers = List.of(
			new Player("Avery", "MI", Position.PITCHER, false, new HashMap<>() {{  put("ip", 7d); }}),
			new Player("Sean", "CA", Position.PITCHER, false, new HashMap<>() {{ put("ip", 4d); }})
		);
		fantasyService = new FantasyService(players);
		
		// Act
		Result result = fantasyService.performPOverall(null);
		List<PlayerValuation> playerValuations = (List<PlayerValuation>) result.getPayload();
		
		// Assert
		assertTrue(result.successful());
		assertEquals(2, playerValuations.size());
		assertEquals(expectedPlayers.get(0), playerValuations.get(0).getPlayer());
		assertEquals(expectedPlayers.get(1), playerValuations.get(1).getPlayer());
	}
	
	@Test
	public void performPOverall_fivePitchers_failure() {
		// Arrange
		Player pitcher = new Player("sean", "team", Position.PITCHER, false, null);
		List<Player> players = List.of(pitcher, pitcher, pitcher, pitcher, pitcher);
		Map<String, List<Player>> playerMap = new HashMap<>() {{ 
			put("A", players);
		}};
		fantasyService.setPlayerMap(playerMap);
		
		// Act
		Result result = fantasyService.performPOverall(null);
		
		// Assert
		assertFalse(result.successful());
		assertTrue(result.getMessage().contains("already drafted five pitchers"));
	}
	
	private List<Player> getPOverallTestPlayers() {
		return List.of(
			new Player("Batoul", "NY", Position.PITCHER, true, new HashMap<>() {{ put("ip", 5d); }}),
			new Player("Sean", "CA", Position.PITCHER, false, new HashMap<>() {{ put("ip", 4d); }}),
			new Player("Jared", "MI", Position.CENTER_FIELD, false, new HashMap<>() {{ put("ip", 3d); }}),
			new Player("Avery", "MI", Position.PITCHER, false, new HashMap<>() {{  put("ip", 7d); }}),
			new Player("Jane", "NV", Position.PITCHER, false, new HashMap<>() {{  put("random stat", 8d); }})
		);
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
	
	@Test
	public void performPEvalFun_emptyString_false() {
		// Arrange
		List<String> args = List.of("  ");
		
		// Act
		Result result = fantasyService.performPEvalFun(args);
		
		// Assert
		assertFalse(result.successful());
		assertTrue(result.getMessage().contains("enter an expression"));
	}
	
	@Test
	public void performWeight_true() {
		// Arrange
		List<String> args = List.of("p 1.6 c 3");
		
		// Act
		Result result = fantasyService.performWeight(args);
		Map<Position, Double> weights = fantasyService.getWeights();
		
		// Assert
		assertTrue(result.successful());
		assertEquals(Position.values().length, weights.size());
		assertEquals(Double.valueOf(1.6), weights.get(Position.PITCHER));
		assertEquals(Double.valueOf(3), weights.get(Position.CATCHER));
		for (Position position : Position.values()) {
			if (position != Position.PITCHER && position != Position.CATCHER) {
				assertEquals(Double.valueOf(1), weights.get(position));
			}
		}
	}
	
	@Test
	public void performWeight_nullArgs_false() {
		// Act
		Result result = fantasyService.performWeight(null);
		
		// Assert
		assertFalse(result.successful());
		assertTrue(result.getMessage().contains("enter an expression"));
	}
	
	@Test
	public void performWeight_emptyArgs_false() {
		// Act
		Result result = fantasyService.performWeight(new ArrayList<>());
		
		// Assert
		assertFalse(result.successful());
		assertTrue(result.getMessage().contains("enter an expression"));
	}
	
	@Test
	public void performWeight_missingArg_false() {
		// Arrange
		List<String> args = List.of("c");
		
		// Act
		Result result = fantasyService.performWeight(args);
		
		// Assert
		assertFalse(result.successful());
		assertTrue(result.getMessage().contains("each position has a corresponding weight"));
	}
	
	@Test
	public void performWeight_invalidPosition_false() {
		// Arrange
		List<String> args = List.of("pitcher 1.6");
		
		// Act
		Result result = fantasyService.performWeight(args);
		
		// Assert
		assertFalse(result.successful());
		assertTrue(result.getMessage().contains("pitcher is not a valid position"));
	}
	
	@Test
	public void performWeight_invalidNumber_false() {
		// Arrange
		List<String> args = List.of("p a.5");
		
		// Act
		Result result = fantasyService.performWeight(args);
		
		// Assert
		assertFalse(result.successful());
		assertTrue(result.getMessage().contains("a.5 is not a valid number"));
	}
	
	@Test
	public void performWeight_wrongOrderArgs_false() {
		// Arrange
		List<String> args = List.of("1.6 p");
		
		// Act
		Result result = fantasyService.performWeight(args);
		
		// Assert
		assertFalse(result.successful());
		assertTrue(result.getMessage().contains("1.6 is not a valid position"));
	}
}
