package tests.unit_tests.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import main.enums.Position;
import main.models.Player;
import main.utils.FileUtils;

public class FileUtilsTests {
	
	@Before
	public void setup() {
		Map<String, Double> stats = new HashMap<>() {{
			put("IP", 0.45);
			put("ERA", 0.5);
			put("BA", 0.245);
			put("SLG", 0.95);
			put("RBI", 6.95);
		}};
		List<Player> players = List.of(new Player("player 1", "team1", (Position.PITCHER), false, stats));
	}

	@Test
	public void parsePositionDetails_positionsPresent_success() {
		// Arrange
		String positionDetails = "1/2/3D/4/56789";
		
		
		// Act
		Position position = FileUtils.parsePositionDetails(positionDetails);
		
		
		// Assert
		assertEquals(Position.PITCHER, position);
	}
	
	@Test
	public void parsePositionsDetails_noPositionsPresent_success() {
		// Arrange
		String positionDetails = "";
		
		// Act
		Position position = FileUtils.parsePositionDetails(positionDetails);
		
		// Assert
		assertTrue(position == null);
	}
	
}
