package tests.integration_tests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import main.enums.Position;
import main.models.Player;
import main.models.Result;
import main.services.FantasyService;
import main.utils.Evaluator;
import main.utils.FileUtils;

public class FileUtilsTests {

	private static String fileDirectory = System.getProperty("user.dir") + "/src/tests/integration_tests/test_files";
	private FantasyService fantasyService;
	
	@Before
	public void setup() {
		fantasyService = new FantasyService();
	}
	
	@Test
	public void performSave_success() {
		// Arrange
		List<String> args = List.of("savePlayers");
		
		// Act
		Result result = FileUtils.save(args, fileDirectory, fantasyService);
		
		// Assert
		assertTrue(result.successful());
	}
	
	@Test
	public void performSave_noArgs_false() {
		// Act
		Result result = FileUtils.save(null, fileDirectory, fantasyService);
		
		// Assert
		assertFalse(result.successful());
		assertEquals(result.getMessage(), "please enter a filename");
	}
	
	@Test
	public void restore_success() throws IOException {
		// Arrange
		Evaluator pitcherEvaluator = new Evaluator("1.05 * OBP + SLG");
		Evaluator batterEvaluator = new Evaluator("SO / IP + ERA");
		Map<String, List<Player>> playerMap = new HashMap<>();
		playerMap.put("A", List.of(new Player("Sean")));
		
		FantasyService tempFantasyService = new FantasyService();
		tempFantasyService.getWeights().put(Position.CATCHER, Double.valueOf(2));
		tempFantasyService.setPitcherEvaluator(pitcherEvaluator);
		tempFantasyService.setBatterEvaluator(batterEvaluator);
		tempFantasyService.setPlayerMap(playerMap);
		
		String fileName = "fake_state";
		save(tempFantasyService, fileName);
		
		// Act
		Result result = FileUtils.restore(List.of(fileName), fileDirectory, fantasyService);
		
		// Assert
		assertTrue(result.successful());
		assertEquals(pitcherEvaluator, fantasyService.getPitcherEvaluator());
		assertEquals(batterEvaluator, fantasyService.getBatterEvaluator());
		assertEquals(playerMap, fantasyService.getPlayerMap());
		assertEquals(tempFantasyService.getWeights(), fantasyService.getWeights());
	}
	
	private void save(FantasyService fantasyService, String fileName) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(fileDirectory + "/" + fileName + ".dat");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		
		objectOutputStream.writeObject(fantasyService.getPitcherEvaluator());
		objectOutputStream.writeObject(fantasyService.getBatterEvaluator());
		objectOutputStream.writeObject(fantasyService.getPlayerMap());
		objectOutputStream.writeObject(fantasyService.getWeights());
		
		objectOutputStream.close();
		fileOutputStream.close();
	}

	@Test
	public void restore_nullArgs_failure() {
		// Act
		Result result = FileUtils.restore(null, fileDirectory, fantasyService);
		
		// Assert
		assertFalse(result.successful());
		assertTrue(result.getMessage().contains("enter the name of the file"));
	}
	
	@Test
	public void restore_emptyArgs_failure() {
		// Act
		Result result = FileUtils.restore(new ArrayList<>(), fileDirectory, fantasyService);
		
		// Assert
		assertFalse(result.successful());
		assertTrue(result.getMessage().contains("enter the name of the file"));
	}
	
	@Test
	public void restore_fileDoesNotExist_failure() {
		// Arrange
		List<String> args = List.of("random file");
		
		// Act
		Result result = FileUtils.restore(args, fileDirectory, fantasyService);
		
		// Assert
		assertFalse(result.successful());
		assertTrue(result.getMessage().contains("file does not exist"));
	}
	
	@Test
	public void readBattingStats_success() throws Exception {
		// Arrange
		String fileName = "fake_batting_stats.txt";
		
		Map<String, Double> name3Stats = new HashMap<>() {{ put("stat", Double.valueOf(".223")); }};
		Map<String, Double> name4Stats = new HashMap<>() {{ put("stat", Double.valueOf(".4")); }};
		List<Player> expectedPlayers = List.of(
				new Player("name3", "DET", Position.FIRST_BASE, false, name3Stats),
				new Player("name4", "NY", Position.SECOND_BASE, false, name4Stats)
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
				new Player("name1", "DET", (Position.PITCHER), false, name1Stats),
				new Player("name2", "NY", (Position.PITCHER), false, name2Stats)
		);
		
		// Act
		List<Player> actualPlayers = FileUtils.readPitchingStats(fileDirectory, fileName);
		
		// Assert
		assertEquals(expectedPlayers, actualPlayers);
	}
}
