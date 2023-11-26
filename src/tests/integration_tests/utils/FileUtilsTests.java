package tests.integration_tests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import main.enums.Position;
import main.models.Player;
import main.utils.FileUtils;

public class FileUtilsTests {

	private static String fileDirectory = System.getProperty("user.dir") + "/src/tests/integration_tests/test_files";
	
	@Test
	public void save_success() {
		
	}
	
	@Test
	public void save_failure() {
		
	}
	
	@Test
	public void restore_success() {
		
	}
	
	@Test
	public void restore_failure() {
		
	}
	
	@Test
	public void readBattingStats_success() throws Exception {
		// Arrange
		String fileName = "fake_batting_stats.txt";
		
		Map<String, Double> name3Stats = new HashMap<>() {{ put("stat", Double.valueOf(".223")); }};
		Map<String, Double> name4Stats = new HashMap<>() {{ put("stat", Double.valueOf(".4")); }};
		List<Player> expectedPlayers = List.of(
				new Player("name3", "DET", List.of(Position.FIRST_BASE), false, name3Stats),
				new Player("name4", "NY", List.of(Position.SECOND_BASE), false, name4Stats)
		);
		
		// Act
		List<Player> actualPlayers = FileUtils.readBattingStats(fileDirectory, fileName);
		
		// Assert
		assertEquals(expectedPlayers, actualPlayers);
	}
	
	@Test
	public void readPitchingStats_success() throws Exception {
		// Arrange
		String fileName = "fake_pitching_stats.txt";
		
		Map<String, Double> name1Stats = new HashMap<>() {{ put("stat", Double.valueOf(".223")); }};
		Map<String, Double> name2Stats = new HashMap<>() {{ put("stat", Double.valueOf(".4")); }};
		List<Player> expectedPlayers = List.of(
				new Player("name1", "DET", List.of(Position.PITCHER), false, name1Stats),
				new Player("name2", "NY", List.of(Position.PITCHER), false, name2Stats)
		);
		
		// Act
		List<Player> actualPlayers = FileUtils.readPitchingStats(fileDirectory, fileName);
		
		// Assert
		assertEquals(expectedPlayers, actualPlayers);
	}
}
