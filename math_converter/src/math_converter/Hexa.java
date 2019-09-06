package math_converter;

public class Hexa {

	public static String getDeci(String hexa) {
		switch (hexa) {
			case "A":
				return "10";
			case "B":
				return "11";
			case "C":
				return "12";
			case "D":
				return "13";
			case "E":
				return "14";
			case "F":
				return "15";
			default:
				return hexa;
		}
	}
	

	public static String getHexa(String decimal) {
		switch (decimal) {
			case "10":
				return "A";
			case "11":
				return "B";
			case "12":
				return "C";
			case "13":
				return "D";
			case "14":
				return "E";
			case "15":
				return "F";
			default:
				return decimal;
		}
	}
}
	
	
