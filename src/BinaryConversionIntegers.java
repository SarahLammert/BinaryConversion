import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BinaryConversionIntegers {
	
	public static final int LONG = 64;
	public static final int INT = 32;
	public static final int SHORT = 16;
	public static final int BYTE = 8;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File("C:\\Users\\sarahlammert\\eclipse-workspace\\BinaryConverterIn\\checkFile.txt");
		Scanner s = new Scanner(file);
		
		while(s.hasNextLine()) {
			try {
				long x = s.nextLong();
				System.out.println("Here is the binary number for " + x + "\n" + binConvert(x));
			} catch(NumberFormatException ne) {
				System.out.println("That is not a valid number or the number is too big");
				break;
			}
		}
	}

	public static String binConvert(long x) {

		String posString = "";
		String comString = "";
		String bckString = "";
		String negString = "";
		
		int bits = 0;
		long fakeX = x;
		boolean isNeg = false;
		boolean stopNeg = false;
		boolean firstTime = true;
		if(x < 0) {
			isNeg = true;
			fakeX *= -1;
		}
		
		if(x <= (Math.pow(2, 7)-1)) {
			bits = BYTE;
		}
		else if(x <= (Math.pow(2, 15)-1)) {
			bits = SHORT;
		}
		else if(x <= (Math.pow(2, 31)-1)) {
			bits = INT;
		}
		else if(x <= (Math.pow(2, 63)-1)) {
			bits = LONG;
		}
		
		System.out.println(bits);
		
		for(int c = bits; c >= 1; c--) {
			
			if(x == (long)(Math.pow(2, 63))) {
				return "0111111111111111111111111111111111111111111111111111111111111111";
			}
			else if(x == (long)((Math.pow(2, 63)*-1)-1)) {
				return "1000000000000000000000000000000000000000000000000000000000000000";
			}
			else if(fakeX >= (long)(Math.pow(2, c-1))) {
					fakeX -= (long)(Math.pow(2, c-1));
					posString += "1";
				} else {
					posString += "0";
				}
		}
		
		if(isNeg) {
			for(int y = 0; y <= posString.length()-1; y++) {
				if(posString.charAt(y) == '1') {
					comString += "0";
				} else {
					comString += "1";
				}
			}
			
			for(int i = (comString.length()-1); i >= 0; i--) {
				if(comString.charAt(i) == '1' && !stopNeg) {
					bckString += "0";
				} else {
					if(firstTime) {
						bckString += "1";
						firstTime = false;
						stopNeg = true;
					} else {
						if(comString.charAt(i) == '1') {
							bckString += "1";
						} else {
							bckString += "0";
						}
					}
				}
			}
			for(int p = (bckString.length()-1); p >= 0; p--) {
				if(bckString.charAt(p) == '1') {
					negString += "1";
				} else {
					negString += "0";
				}
			}
		}
		
		System.out.println("This is posString: " + posString); //For positive numbers in binary
		System.out.println("This is comString: " + comString); //To flip the bits
		System.out.println("This is bckString: " + bckString); //To add the one to the bits but also flips the order
		System.out.println("This is negString: " + negString); //To reorder
		
		if(isNeg) {
			return negString;
		}
		else {
			return posString;
		}
	}
}