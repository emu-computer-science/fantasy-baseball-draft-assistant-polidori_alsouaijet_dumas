package tests.unit_tests.utils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import main.enums.Position;
import main.utils.FileUtils;

public class FileUtilsTests {

	@Test
	public void parsePositionDetails_positionsPresent_success() {
		// Arrange
		String positionDetails = "1/2/3D/4/56789";
		Position[] expectedPositions = Position.values();
		
		// Act
		Position[] positions = FileUtils.parsePositionDetails(positionDetails).stream().toArray(Position[]::new);
		
		
		// Assert
		Arrays.sort(expectedPositions, (p1, p2) -> p1.compareTo(p2));	// Sort to ensure elements align
		Arrays.sort(positions, (p1, p2) -> p1.compareTo(p2));
		assertArrayEquals(expectedPositions, positions);
	}
	
	@Test
	public void parsePositionsDetails_noPositionsPresent_success() {
		// Arrange
		String positionDetails = "";
		
		// Act
		List<Position> positions = FileUtils.parsePositionDetails(positionDetails);
		
		// Assert
		assertTrue(positions.size() == 0);
	}
}
