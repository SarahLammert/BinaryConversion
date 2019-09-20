import java.util.InputMismatchException;
import java.util.Scanner;

public class BinaryConversionDecimalFractions {
	public static final int INT = 32;
	
	public static void main(String[] args) {
		//Trying to get a decimal number
		System.out.println("Please type a pos decimal number(eg 10.39)");
		try {
			Scanner s = new Scanner(System.in);
			double n = s.nextDouble();
			s.close();
			System.out.println(decimalConverter(n));
		} catch(InputMismatchException e) {
			System.out.println("You didn't input a valid decimal. Please restart program!");
		}
	}
	//Finding the binary
	public static String decimalConverter(double n) {
		double num = n;
		String fullS = "";
		boolean cutOff = false;
		boolean notDone = true;
		
		//Finding the part to the left of the decimal
		double l = (int)num;
		System.out.println(l);
		
		//Finding the part to the right of the decimal 
		String oR = String.valueOf(n);
		int indexOfDot = oR.indexOf('.');
		oR = oR.substring(indexOfDot);
		double r = Double.valueOf(oR);
		System.out.println(r);
		
		//Finding the left part of the number in binary
		for(int c = INT; c >= 1; c--) {
			if(l >= Math.pow(2, c-1)) {
				l -= Math.pow(2, c-1);
				fullS += "1";
				cutOff = true;
			} else {
				if(cutOff) {
					fullS += "0";
				}
			}
		}
		
		fullS += ".";
		System.out.println("First part done");
		
		//Finding the right part of the number in binary
		while(notDone) {
			r *= 2.0;
			System.out.println("");
			System.out.println(r);
			if(r >= 1.0) {
				fullS += "1";
				r -= 1.0;
				int t = (int)(r*1000.0);
			    r = ((double)t)/1000.0;
				System.out.println(r);
			} else if(r == 0){
				notDone = false;
			} else {
				fullS += "0";
			}
		}
		return fullS;
	}
}
