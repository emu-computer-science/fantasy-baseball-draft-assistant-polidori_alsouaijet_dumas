package tests.unit_tests.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import main.enums.Position;
import main.models.Player;

public class PlayerTests {

	@Test
	public void equals_sameContent_true() {
		// Arrange
		List<Position> player1Positions = List.of(Position.CATCHER);
		Map<String, Double> player1Stats = new HashMap<>() {{
			put("stat", 0.45);
			put("stat2", 5d);
		}};
		Player player1 = new Player("name", "team", player1Positions, false, player1Stats);
				
		List<Position> player2Positions = List.of(Position.CATCHER);
		Map<String, Double> player2Stats = new HashMap<>() {{
			put("stat", 0.45);
			put("stat2", 5d);
		}};
		Player player2 = new Player("name", "team", player2Positions, false, player2Stats);
		
		// Act
		boolean equal = player1.equals(player2);
		
		// Assert
		assertTrue(equal);
	}
	
	@Test
	public void equals_differentContent_false() {
		// Arrange
		List<Position> player1Positions = List.of(Position.CATCHER);
		Map<String, Double> player1Stats = new HashMap<>() {{
			put("stat", 0.45);
			put("stat2", 5d);
		}};
		Player player1 = new Player("name", "team", player1Positions, false, player1Stats);
				
		List<Position> player2Positions = List.of(Position.PITCHER);
		Map<String, Double> player2Stats = new HashMap<>() {{
			put("stat", 0.45);
			put("stat2", 5d);
		}};
		Player player2 = new Player("name2", "team2", player2Positions, false, player2Stats);
		
		// Act
		boolean equal = player1.equals(player2);
		
		// Assert
		assertFalse(equal);
	}
}
