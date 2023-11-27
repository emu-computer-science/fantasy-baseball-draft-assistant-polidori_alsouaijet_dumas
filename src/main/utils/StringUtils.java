package main.utils;

import java.util.regex.Pattern;

public class StringUtils {

	private static Pattern integerPattern = Pattern.compile("^[0-9]+$");
	private static Pattern doublePattern = Pattern.compile("^[0-9]*\\.[0-9]+$");
	
	public static boolean isNumber(String string) {
		if (integerPattern.matcher(string).matches() || doublePattern.matcher(string).matches()) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isOperator(String component) {
		if (component.equals("+") || component.equals("-") || component.equals("/") || component.equals("*")) {
			return true;
		}
		
		return false;
	}
	
}
