package tests.unit_tests.utils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import main.enums.Position;
import main.utils.FileUtils;

public class FileUtilsTests {

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
