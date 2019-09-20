import java.util.InputMismatchException;
import java.util.Scanner;

public class BinaryConversionFloat {
	
	public static final int SINGLE = 32;
	public static final int BIAS = 127;
	
	public static void main(String[] args) {
		
		System.out.println("Please type a pos decimal number(eg 10.39)");
		try {
			Scanner s = new Scanner(System.in);
			double num = s.nextDouble();
			s.close();
			boolean isPos = isPos(num);
			if(!isPos) {
				num *= -1;
			}
			
			String floatNum = decimalConverter(num, signBit(isPos));
			System.out.println("The IEEE Standard 754 for " + num + " is " + floatNum);
			
		} catch(InputMismatchException e) {
			System.out.println("You didn't input a valid value. Please restart program!");
		}
	}
	
	public static boolean isPos(double n) {
		if(n < 0) {
			return false;
		} else {
			return true;
		}
	}
	public static String signBit(boolean iP) {
		if(iP) {
			return "0";
		} else {
			return "1";
		}
	}
	
	public static String decimalConverter(double n, String sb) {
		//Parts
		String signBit = sb;
		String normalBin = "";
		String exponentBin = "";
		String fractionBin = "";
		
		
		String fullBin = "";
		String binary = "";
		
		//Temp
		String flipBinOne = "";
		
		int numOne = (int)n;
		int numTwo = (int)n;
		double newNum = (double)numOne;
		
		//Just decimal part
		double fractionPart = n - newNum;
		
		//Integer part
		while(numOne > 0) {
			if(numOne % 2 == 1) {
				flipBinOne += "1";
			} else {
				flipBinOne += "0";
			}
			numOne = numOne / 2;
		}
		
		for(int x = flipBinOne.length()-1; x >= 0; x--) {
			if(flipBinOne.charAt(x) == '1') {
				normalBin += "1";
			} else {
				normalBin += "0";
			}
		}
		
		//Decimal part
		for(int y = 0; y <= (23 - normalBin.length()); y++) {
			fractionPart *= 2;
			if(fractionPart >= 1) {
				fractionPart -= 1;
				fractionBin += "1";
			} else {
				fractionBin += "0";
			}
		}
		
		//Finding out the exponent
		int norm = Integer.parseInt(normalBin);
		int c = 0;
		while(norm >= 10) {
			norm *= Math.pow(10, -1);
			c++;
		}

		int exponent = c + BIAS;
		String flipBinTwo = "";
		
		while(exponent > 0) {
			if(exponent % 2 == 1) {
				flipBinTwo += "1";
			} else {
				flipBinTwo += "0";
			}
			exponent = exponent / 2;
		}
		
		for(int x = flipBinTwo.length()-1; x >= 0; x--) {
			if(flipBinTwo.charAt(x) == '1') {
				exponentBin += "1";
			} else {
				exponentBin += "0";
			}
		}
		fullBin = signBit + exponentBin + normalBin.substring(1) + fractionBin;
		String orderedFullBin = "";
		for(int z = 0; z <= fullBin.length()-4; z += 4) {
			orderedFullBin += fullBin.substring(z, z + 4);
			orderedFullBin += " ";
		}
		
		return orderedFullBin;
	}
}
