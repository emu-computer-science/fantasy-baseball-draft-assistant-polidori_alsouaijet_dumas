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
		
		Map<String, Double> player1Stats = new HashMap<>() {{
			put("stat", 0.45);
			put("stat2", 5d);
		}};
		Player player1 = new Player("name", "team", Position.CATCHER, false, player1Stats);
				
		Map<String, Double> player2Stats = new HashMap<>() {{
			put("stat", 0.45);
			put("stat2", 5d);
		}};
		Player player2 = new Player("name", "team", Position.CATCHER, false, player2Stats);
		
		// Act
		boolean equal = player1.equals(player2);
		
		// Assert
		assertTrue(equal);
	}
	
	@Test
	public void equals_differentContent_false() {
		// Arrange

		Map<String, Double> player1Stats = new HashMap<>() {{
			put("stat", 0.45);
			put("stat2", 5d);
		}};
		Player player1 = new Player("name", "team", Position.CATCHER, false, player1Stats);
				

		Map<String, Double> player2Stats = new HashMap<>() {{
			put("stat", 0.45);
			put("stat2", 5d);
		}};
		Player player2 = new Player("name2", "team2", Position.PITCHER, false, player2Stats);
		
		// Act
		boolean equal = player1.equals(player2);
		
		// Assert
		assertFalse(equal);
	}
}
