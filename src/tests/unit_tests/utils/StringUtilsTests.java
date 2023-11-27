package tests.unit_tests.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import main.utils.StringUtils;

public class StringUtilsTests {

	@Test
	public void isNumber_integer_true() {
		// Arrange
		String string = "23";
		
		// Act
		boolean isNumber = StringUtils.isNumber(string);
		
		// Assert
		assertTrue(isNumber);
	}
	
	@Test
	public void isNumber_doubleWithNoLeadingDigit_true() {
		// Arrange
		String string = ".123";
		
		// Act
		boolean isNumber = StringUtils.isNumber(string);
		
		// Assert
		assertTrue(isNumber);
	}
	
	@Test
	public void isNumber_doubleWithLeadingDigit_true() {
		// Arrange
		String string = "0.123";
		
		// Act
		boolean isNumber = StringUtils.isNumber(string);
		
		// Assert
		assertTrue(isNumber);
	}
	
	@Test
	public void isNumber_false() {
		// Arrange
		String string = "test";
		
		// Act
		boolean isNumber = StringUtils.isNumber(string);
		
		// Assert
		assertFalse(isNumber);
	}
}
