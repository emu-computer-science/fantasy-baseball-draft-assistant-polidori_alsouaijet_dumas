package main.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.enums.Position;
import main.models.Player;
import main.models.Result;
import main.services.FantasyService;

public class FileUtils {
	
	private static String rootDirectory = System.getProperty("user.dir") + "/files";

	public static Result save(List<String> args, FantasyService fantasyService) {
		
		if (args.size() == 0) 
			return new Result(false, "please enter a filename");
		try {
			String fileName = args.get(0);
			
			FileOutputStream file = new FileOutputStream(rootDirectory + "/" + fileName + ".dat");
			ObjectOutputStream out = new ObjectOutputStream(file);
	
			out.writeObject(fantasyService.getPitcherEvaluator());
			out.writeObject(fantasyService.getBatterEvaluator());
			out.writeObject(fantasyService.getPlayerMap());
			out.close();
			file.close();
		}

		catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("☆○o☆○o☆○o");
			return new Result(false, "serialization failed");
		}
		
		return new Result(true, null);
	}
	
	public static Result restore(List<String> args, FantasyService fantasyService) {
		if (args.size() == 0) {
			return new Result(false, "Please enter the name of the file.");
		} 
		
		
		File file = new File(rootDirectory + "/" + args.get(0) + ".dat");
		if (!file.exists()) {
			return new Result(false, "The file does not exist. Please ensure the file is present in the 'files' folder.");
		}
		
		try {
			FileInputStream fileOutputStream = new FileInputStream(file);
			ObjectInputStream objectOutputStream = new ObjectInputStream(fileOutputStream);
			
			Evaluator pitcherEvaluator = (Evaluator)objectOutputStream.readObject();
			Evaluator batterEvaluator = (Evaluator)objectOutputStream.readObject();
			Map<String, List<Player>> playerMap = (Map<String, List<Player>>)objectOutputStream.readObject();
			
			fantasyService.setPitcherEvaluator(pitcherEvaluator);
			fantasyService.setBatterEvaluator(batterEvaluator);
			fantasyService.setPlayerMap(playerMap);
			
			return new Result(true, null, (Object) fantasyService);
		} catch (Exception ex) {
			return new Result(false, "An exception occurred while trying to read the file.");
		}
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
				  							  .filter(player -> !player.getPosition().equals(Position.PITCHER))
				  							  .toList();
	}
	
	private static List<Player> readStatsFile(File statsFile) throws FileNotFoundException {
		Scanner scanner = new Scanner(statsFile);
		
		String[] headers = scanner.nextLine().split(",");
		
		List<Player> players = new ArrayList<>();
		while (scanner.hasNextLine()) {
			boolean skipPlayer = false;
			String[] playerDetails = scanner.nextLine().split(",");
					
			Player newPlayer = new Player();
			Map<String, Double> stats = new HashMap<>();
			for (int i = 1; i < headers.length; i++) {
				String header = headers[i].toLowerCase();
				
				if (header.equals("name")) {
					newPlayer.setName(playerDetails[i].replaceAll("[!@#$%^&*()]", ""));
				} else if (header.equals("tm")) {
					newPlayer.setTeam(playerDetails[i]);
				} else if (header.contains("pos")) {
					Position position = parsePositionDetails(playerDetails[i]);
					if (position == null) {
						skipPlayer = true;
						break;
					}
					newPlayer.setPosition(position);
				} else {
					if (i < playerDetails.length && TypeUtils.isNumber(playerDetails[i])) {
						String stat = playerDetails[i];
						stats.put(header, Double.valueOf(stat));
					}
				}
			}
			
			if (skipPlayer) {
				continue;
			}
			
			newPlayer.setStats(stats);
			players.add(newPlayer);
		}
		
		scanner.close();
		
		return players;
	}

	public static Position parsePositionDetails(String positionDetails) {
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
				
			}
			
			if(position != null) {
				return position;
			}
		}
		
		return null;
	}

	public static List<Player> readPitchingStats(String rootDirectory, String fileName) throws Exception {
		File pitchingStatsFile = new File(rootDirectory + "/" + fileName);
		if (!pitchingStatsFile.exists()) {
			throw new Exception("Pitching stats not found");
		}
		
		List<Player> players = readStatsFile(pitchingStatsFile);
		for (Player player : players) {
			player.setPosition(Position.PITCHER);
		}
		
		return players;
	}
	
}
