package main.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.enums.Position;
import main.models.Player;

public class FileUtils {
	
	private static String rootDirectory = System.getProperty("user.dir") + "/files";

	public static void save() {
		
	}
	
	public static void restore() {
		
	}
	
	public static List<Player> readInPlayers(String battingStatsFileName, String pitchingStatsFileName) throws Exception {
		List<Player> players = readBattingStats(rootDirectory, battingStatsFileName);
		List<Player> pitchers = readPitchingStats(rootDirectory, pitchingStatsFileName);
		
		// Add to pitchers because players is an unmodifiable list
		pitchers.addAll(players);

		return pitchers;
	}
	
	public static List<Player> readBattingStats(String rootDirectory, String fileName) throws Exception {
		File battingStatsFile = new File(rootDirectory + "/" + fileName);
		if (!battingStatsFile.exists()) {
			throw new Exception("Batting stats not found");
		}
	
		return readStatsFile(battingStatsFile).stream()
				  							  .filter(player -> !player.getPositions().contains(Position.PITCHER))
				  							  .toList();
	}
	
	private static List<Player> readStatsFile(File statsFile) throws FileNotFoundException {
		Scanner scanner = new Scanner(statsFile);
		
		String[] headers = scanner.nextLine().split(",");
		
		List<Player> players = new ArrayList<>();
		while (scanner.hasNextLine()) {
			String[] playerDetails = scanner.nextLine().split(",");
					
			Player newPlayer = new Player();
			Map<String, Double> stats = new HashMap<>();
			for (int i = 1; i < headers.length; i++) {
				String header = headers[i].toLowerCase();
				
				if (header.equals("name")) {
					newPlayer.setName(playerDetails[i]);
				} else if (header.equals("tm")) {
					newPlayer.setTeam(playerDetails[i]);
				} else if (header.contains("pos")) {
					List<Position> positions = parsePositionDetails(playerDetails[i]);
					newPlayer.setPositions(positions);
				} else {
					if (i < playerDetails.length && TypeUtils.isNumber(playerDetails[i])) {
						String stat = playerDetails[i];
						stats.put(header, Double.valueOf(stat));
					}
				}
			}
			
			newPlayer.setStats(stats);
			players.add(newPlayer);
		}
		
		scanner.close();
		
		return players;
	}

	public static List<Position> parsePositionDetails(String positionDetails) {
		List<Position> positions = new ArrayList<>();
		for (char c : positionDetails.toCharArray()) {
			Position position = null;
			
			switch (c) {
				case '1':
					position = Position.PITCHER;
					break;
				case '2':
					position = Position.CATCHER;
					break;
				case '3':
					position = Position.FIRST_BASE;
					break;
				case '4':
					position = Position.SECOND_BASE;
					break;
				case '5':
					position = Position.THIRD_BASE;
					break;
				case '6':
					position = Position.SHORT_STOP;
					break;
				case '7':
					position = Position.LEFT_FIELD;
					break;
				case '8':
					position = Position.CENTER_FIELD;
					break;
				case '9':
					position = Position.RIGHT_FIELD;
					break;
				default:
					break;
			}
			
			if (position != null) {
				positions.add(position);
			}
		}
		
		return positions;
	}

	public static List<Player> readPitchingStats(String rootDirectory, String fileName) throws Exception {
		File pitchingStatsFile = new File(rootDirectory + "/" + fileName);
		if (!pitchingStatsFile.exists()) {
			throw new Exception("Pitching stats not found");
		}
		
		List<Player> players = readStatsFile(pitchingStatsFile);
		for (Player player : players) {
			player.setPositions(List.of(Position.PITCHER));
		}
		
		return players;
	}
	
}
