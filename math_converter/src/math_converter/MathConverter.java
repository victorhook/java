package math_converter;

public class MathConverter {

	
	public static String hexaToDeci(String hexa) {
		int n = hexa.length();
		int decimal = 0;
		
		for (int i=0; i<=n-1; i++) {
			String lastChar = hexa.substring((n-1 -i), (n - i));
			int lastDigit = Integer.parseInt(Hexa.getDeci(lastChar));
			decimal += lastDigit * Math.pow(16, i); 
		}
		return String.valueOf(decimal);
	}

	
	public static String deciToHexa(String deci) {
		int remainder;
		int decimal = Integer.parseInt(deci);
		String hexa = "";
		
		while (decimal != 0) {
			remainder = decimal % 16;
			decimal /= 16;
			hexa += Hexa.getHexa(String.valueOf(remainder));
			}
		return reverseString(hexa);
	}
	
	
	public static String octalToDeci(String octal) {
		int decimal = 0;
		int n = octal.length();
		
		for (int i=0; i<=n-1; i++) {
			char lastChar = octal.charAt(n - (i+1));
			int lastDigit = Character.getNumericValue(lastChar);
			decimal += lastDigit * Math.pow(8, i);
		}
		return String.valueOf(decimal);
	}
	
	
	public static String deciToOctal(String deci) {
		int remainder;
		int decimal = Integer.parseInt(deci); 
		String octo = "";
		
		while (decimal != 0) {
			remainder = decimal % 8;
			decimal /= 8;
			octo += remainder;
		}
		return reverseString(octo);
	}
	

	public static String binToDeci(String bin) {
		int n = bin.length();
		int decimal = 0;
		
		for (int i=0; i<=n-1; i++) {
			char lastChar = bin.charAt(n - (i+1));
			int LSB = Character.getNumericValue(lastChar);
			decimal += LSB * Math.pow(2, i);
		}
		return String.valueOf(decimal);
	}
	
	
	public static String deciToBin(String deci) {
		int decimal = Integer.parseInt(deci);
		int remainder;
		String binary = "";
		
		while (decimal != 0) {
			remainder = decimal % 2;
			decimal /= 2;
			binary += Integer.toString(remainder);
		}
		return reverseString(binary);	
	}
	
	
	public static String reverseString(String input) {
		String reverseString = "";
		for (int i=input.length()-1; i>=0; i--) {
			reverseString += input.charAt(i);
		}
		return reverseString;
	}
	
}
