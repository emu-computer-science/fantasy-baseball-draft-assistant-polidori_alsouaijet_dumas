package tests.unit_tests.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import main.models.Player;
import main.utils.Evaluator;

public class EvaluatorTests {

	private Evaluator evaluator;
	
	@Before
	public void setup() {
		evaluator = new Evaluator();
	}
	
	@Test
	public void evaluate_noVariables_success() {
		// Arrange
		evaluator.setExpression("5 + 4");

		// Act
		Double result = evaluator.evaluate(null);
		
		// Assert
		assertEquals(Double.valueOf(9), result);
	}
	
	@Test
	public void evaluate_variable_success() {
		// Arrange
		evaluator.setExpression("5 + ERA");
		Double era = 0.5d;
		Double expectedResult = 5 + era;
		
		Map<String, Double> stats = new HashMap<>() {{ put("era", era); }};
		Player player = new Player();
		player.setStats(stats);
		
		// Act
		Double actualResult = evaluator.evaluate(player);
		
		// Assert
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void evaluate_onlyVariable_success() {
		// Arrange
		evaluator.setExpression("ERA");
		Double era = 0.5d;
		
		Map<String, Double> stats = new HashMap<>() {{ put("era", era); }};
		Player player = new Player();
		player.setStats(stats);
		
		// Act
		Double result = evaluator.evaluate(player);
		
		// Assert
		assertEquals(era, result);
	}
	
	@Test
	public void evaluate_multipleVariables_success() {
		// Arrange
		evaluator.setExpression("SO / IP + ERA * 23.6");
		Double so = 1.43d;
		Double ip = 5.9d;
		Double era = 5.8;
		Double expectedResult = so / ip + era * 23.6;
		
		Map<String, Double> stats = new HashMap<>() {{ 
			put("so", so);
			put("ip", ip);
			put("era", era);
		}};
		Player player = new Player();
		player.setStats(stats);
		
		// Act
		Double actualResult = evaluator.evaluate(player);
		
		// Assert
		assertEquals(expectedResult, actualResult);
	}
	
}
