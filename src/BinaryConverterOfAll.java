import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BinaryConverterOfAll {
	
	public static final int BYTE = 8;
	public static final int SHORT = 16;
	public static final int INT = 32;
	public static final int SINGLE = 32;
	public static final int LONG = 64;
	
	public static final int STRING_TYPE = 0;
	public static final int INT_TYPE = 1;
	public static final int FLOAT_TYPE = 2;
	
	public static final int BIAS = 127;
	public static final int MANTISSA = 23;
	    
	public static void main(String[] args) {
		
		int dataType = -1;
		Scanner s = null;
		boolean allGood = false;
		try {
			s = new Scanner(new File("checkFile.txt"));
			allGood = true;
			} catch( Exception e ) {
				System.out.println("It's not open.  There is an issue opening your file.");
	        }
			if(allGood) {
	            while(s != null && s.hasNextLine()) {
	                String nextNumIn = s.nextLine();
	                
	                if(itsNotANumber(nextNumIn)) {
	                    System.out.println(nextNumIn + " is not a valid input");
	                    dataType = STRING_TYPE;
	                } else if (isADecimal(nextNumIn)) {
	                    System.out.println(nextNumIn + " is a decimal");
	                    dataType = FLOAT_TYPE;
	                } else {
	                    System.out.println(nextNumIn + " is an integer");
	                    dataType = INT_TYPE;
	                }
	                
	                if(dataType == INT_TYPE) {
	                    String binInt = "";
	                    try {
	                        long x = Long.parseLong(nextNumIn);
	                        binInt = binConvert(x);
	                        System.out.println(binInt);
	                        
	                    } catch (Exception e) {
	                        System.out.println(nextNumIn + "The number is too large");    
	                        binInt = "" + nextNumIn + " is too big for Long";
	                    }
	                 
	                } else if(dataType == FLOAT_TYPE) {
	                	 try {
	                		 double num = Double.parseDouble(nextNumIn);
	                		 boolean isPos = isPos(num);
	             			 if(!isPos) {
	             				num *= -1;
	             			 }
	                		 String floatNum = decimalConverter(num, signBit(isPos)); 
	             			 System.out.println("The IEEE Standard 754 for " + num + " is " + floatNum);
	             			 
	             			
	                	 } catch(InputMismatchException e) {
	                		 System.out.println("This isn't a valid input.");
	                	 }
	                } else {
	                	System.out.println("This isn't a valid input.");
	                }
	            }
	            s.close();
	        }
	    }
	    
	    public static boolean itsNotANumber(String nextNumIn) {
	        boolean itsNotANumber = false;
	        String nonNums = "abcdefghijklmnopqrstuvwxyz!@#$%^&*()_+= {[}]|\\:;\"'<,>?/";
	        
	        for(int l= 0; l < nonNums.length(); l++) {
	            if(nextNumIn.contains("" + nonNums.charAt(l))) {
	                itsNotANumber = true;
	            }
	        }
	        return itsNotANumber;
	    }
	    
	    public static boolean isADecimal(String nextNumIn) {
	        boolean itsADecimal = false;
	        if(nextNumIn.contains(".")) {
	            itsADecimal = true;
	        }
	        return itsADecimal;
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
			if(isNeg) {
				return negString;
			}
			else {
				return posString;
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
			
			String signBit = sb;
			String normalBin = "";
			String exponentBin = "";
			String fractionBin = "";
			String flipBinOne = "";
			String fullBin = ""; 
			
			int numOne = (int)n;
			int numTwo = numOne;
			double newNum = (double)numOne;
			
			//Just decimal part
			double fractionPart = n - newNum;
			
			//Integer part
			while(numTwo > 0) {
				if(numTwo % 2 == 1) {
					flipBinOne += "1";
				} else {
					flipBinOne += "0";
				}
				numTwo = numTwo / 2;
			}
			
			for(int x = flipBinOne.length()-1; x >= 0; x--) {
				if(flipBinOne.charAt(x) == '1') {
					normalBin += "1";
				} else {
					normalBin += "0";
				}
			}
			
			//Decimal part
			for(int y = 0; y <= (MANTISSA - normalBin.length() + 20); y++) {
				fractionPart *= 2;
				if(fractionPart >= 1) {
					fractionPart -= 1;
					fractionBin += "1";
				} else {
					fractionBin += "0";
				}
			}
			
			int c = 0;
			if(numOne == 0) {
				c = -1 * fractionBin.indexOf('1') - 1;
				
			} else {
				c = normalBin.length() - normalBin.indexOf('1') - 1;
			} 
			
			System.out.println("This is the c value: " + c);
			int exponent = c + BIAS;
			System.out.println("This is the exponent with Bias: " + exponent);
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
			
			while(exponentBin.length() < 8) {
				exponentBin = "0" + exponentBin;
			}
			if(normalBin.length() > 0) {
				normalBin = normalBin.substring(1);
			}
			if(numOne == 0) {
				fractionBin = fractionBin.substring(fractionBin.indexOf('1') + 1, fractionBin.indexOf('1')+1+MANTISSA-normalBin.length());
			} else {
				fractionBin = fractionBin.substring(0, MANTISSA - normalBin.length());
			}
			fullBin = signBit + exponentBin;
			if(numOne == 0) {
				fullBin += fractionBin;
			} else {
				fullBin += normalBin + fractionBin;
			}
			String orderedFullBin = "";
			for(int z = 0; z <= fullBin.length()-4; z += 4) {
				orderedFullBin += fullBin.substring(z, z + 4);
				orderedFullBin += " ";
			}
			return orderedFullBin;
		}
}
