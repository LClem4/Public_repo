package adder;

/**
 * Class to build the ripple adder
 */
public class RippleAdder {

  Gate gate = new Gate();
  private int bitSize;
  private int overflow;
  private String binaryNumber;
  private int decimalNumber;

  /**
   * Specify bit size
   * 
   * @param size
   */
  public RippleAdder(int size) {
    bitSize = size;
    overflow = 0;
  }

  /**
   * Code for full adder
   * 
   * @param b1
   * @param b2
   * @param c
   * @return
   */
  public int[] Full_adder(int b1, int b2, int c) {
    int[] output = new int[2];
    output[0] = gate.XOR(b1, gate.XOR(b2, c));
    output[1] = gate.OR(gate.AND(b1, c), gate.AND(b2, c), gate.AND(b1, b2));
    return output;
  }

  /**
   * Tests full adder
   */
  public void testFullAdder() {
    RippleAdder c = new RippleAdder(16);

    System.out.println("X Y C_in S C_out");
    for (int i = 0; i < 2; i++)
      for (int j = 0; j < 2; j++)
        for (int k = 0; k < 2; k++)
          System.out
              .println(i + " " + j + "   " + k + "  " + c.Full_adder(i, j, k)[0] + "   " + c.Full_adder(i, j, k)[1]);
    System.out.println();
  }

  /**
   * Adds two binary numbers
   * 
   * @param n1
   * @param n2
   * @return
   */
  public String addBinaryNum(String stringNum1, String stringNum2) {

    String newNumString = "";

    if (stringNum1.length() != bitSize || stringNum2.length() != bitSize) {
      return "Error: Enter a/an " + bitSize + " bit binary number";
    }

    int carry = 0; // carry
    int previousCarry = 0;
    int[] adder = new int[2]; // takes the result of the current operation
    int[] newNum = new int[bitSize]; // Builds the new number
    int[] n1 = new int[bitSize];
    int[] n2 = new int[bitSize];

    n1 = stringToInt(stringNum1);
    n2 = stringToInt(stringNum2);

    for (int i = n1.length - 1; i >= 0; i--) {
      adder = Full_adder(n1[i], n2[i], carry);
      newNum[i] = adder[0];
      previousCarry = carry;
      carry = adder[1];
    }
    
    if(gate.XOR(previousCarry, carry) == 1) {
       overflow = 1;
    }
    
    newNumString = intToString(newNum);
  
    return newNumString;
  }

  /**
   * Add numbers that are given as decimal input. (Converts them to binary first)
   */
  public int addDecimalNum(int number1, int number2) {

    int newDecimalNum = -1;
    String stringNum1 = Integer.toBinaryString(number1); // Convert to string
    String stringNum2 = Integer.toBinaryString(number2);
    for (int i = stringNum1.length(); i < bitSize; i++) // Adds 0's to fit bit size
      stringNum1 = "0" + stringNum1;
    for (int i = stringNum2.length(); i < bitSize; i++)
      stringNum2 = "0" + stringNum2;
    stringNum1 = stringNum1.substring(stringNum1.length() - bitSize); // Takes out extra 1's in order to make two's complement
    stringNum2 = stringNum2.substring(stringNum2.length() - bitSize);

    String newNumString = addBinaryNum(stringNum1, stringNum2); // Adds numbers and then converts binary to decimal
    binaryNumber = newNumString; 
    
    if (newNumString.charAt(0) == '1') { // Convert negative to decimal
      newNumString = twoComplement(newNumString);   
      newDecimalNum = -Integer.parseInt(newNumString, 2);
      return newDecimalNum;
    }
    if (newNumString.charAt(0) == '0') { // Convert postive num to decimal
      newDecimalNum = Integer.parseInt(newNumString, 2);
    }

    return newDecimalNum;
  }
  
  /**
   * Tests output
   */
  public void checkOutputs(int number1, int number2) {
    decimalNumber = addDecimalNum(number1, number2); // Sets decimalNumber
    // Overflow and binaryNumber are set along the pathway of decimalNumber
    String strech1 = " " + number1 + " + " + number2 + ": ";
    for(int i = strech1.length(); i < 9+(bitSize/2); i++) {strech1 = strech1 + " ";}
    String strech2 = binaryNumber + " ";
    for(int i = strech2.length(); i < 4+bitSize; i++) {strech2 = strech2 + " ";}
    String strech3 = decimalNumber + " ";
    for(int i = strech3.length(); i < 10; i++) {strech3 = strech3 + " ";}
    String strech4 = overflow + " ";

    System.out.println(strech1 + strech2 + strech3 + strech4);
  }
  
  /**
   * Mimics two's complement
   */
  public String twoComplement(String twoCompNumString) {
    
    int[] lowestNumChecker = new int[bitSize]; // will return number if lowest signed number so don't get incorrect overflow
    lowestNumChecker[0] = 1;
    String StringlowestNumChecker = intToString(lowestNumChecker);
    if(StringlowestNumChecker.contentEquals(twoCompNumString)){
        return twoCompNumString;
    }

    int[] twoCompNum = stringToInt(twoCompNumString);
    int[] newNum = new int[twoCompNum.length]; // Slot for new number

    for (int i = 0; i < twoCompNum.length; i++) { // Flips bits
      newNum[i] = gate.NOT(twoCompNum[i]);
    }

    int[] oneInBinary = new int[twoCompNum.length]; // Creates 1 in binary based on bit size of input
    oneInBinary[twoCompNum.length - 1] = 1;
    String oneInBinaryString = intToString(oneInBinary);

    String newNumString = intToString(newNum); // Converts num to String

    newNumString = addBinaryNum(newNumString, oneInBinaryString); // adds 1[]

    return newNumString;
  }
  
  public int[] stringToInt(String stringNum) {

    int[] newInt = new int[bitSize];
    for (int i = 0; i < stringNum.length(); i++) {
      if (stringNum.charAt(i) == '1')
        newInt[i] = 1;
      else
        newInt[i] = 0;
    }

    return newInt;
  }

  public String intToString(int[] intNumArray) {


    String newNumString = "";
    for (int i = 0; i < intNumArray.length; i++) {
      newNumString = newNumString + intNumArray[i];
    }

    return newNumString;
  }

  /**
   * Main function
   */
  public static void main(String args[]) {

    System.out.println("0. Create and define the bit size for your ripple adder: RippleAdder a1 = new RippleAdder(8 or 16)\n"
                     + "1. Take in two decimal numbers to add: addDecimalNumber(int,int) -> 1.1 convert numbers to binary strings either\n"
                     + "   in binary or two's complement for negative numbers"
                     + "2. addBinaryNum(int,int) -> 2.1 convert binary Strings to int[] numbers -> 2.2 combine 8 or 16 full adders \n"
                     + "   together and add the two binary int array numbers using them ->  2.3 Check for overflow -> 2.4 Convert binary int array numbers \n"
                     + "   back to binary Strings\n"
                     + "3. Convert binary string numbers to decimal numbers\n"
                     + "4. Print out binary number, decimal number, and overflow result\n\n");
        
    System.out.println("RippleAdder 8 bit size\n"
        + "\n"
        + " number1 -> StringNum1 -> n1[] \n"
        + " number2 -> StringNum2 -> n2[] \n"
        + "                                                                                    \n"
        + "            n1[7] n2[7]             n1[1] n2[1]             n1[0] n2[0]             \n"
        + "            _v_ _ _v_ _ _           _v_ _ _v_ _ _           _v_ _ _v_ _ _           \n"
        + "  carry8   |             |  carry6 |             | carry1  |             | carry0   \n"
        + " <--       | full_adder7 | <--...  | full_adder1 | <--...  | full_adder0 | <--      \n"
        + "           |_ _ _ _ _ _ _|         |_ _ _ _ _ _ _|         |_ _ _ _ _ _ _|          \n"  
        + "             v                       v                       v                      \n"
        + "           newNum[7]               newNum[1]               newNum[0]                \n"
        + "\n"
        + " check for Overflow, newNum[] -> numNumString -> newDecimalNum\n\n");
    
    System.out.println(" RippleAdder 16 bit size\n"
        + "\n"
        + " number1 -> StringNum1 -> n1[] \n"
        + " number2 -> StringNum2 -> n2[] \n"
        + "                                                                                    \n"
        + "            n1[15] n2[15]           n1[1] n2[1]             n1[0] n2[0]             \n"
        + "            _v_ _ _v_ _ _           _v_ _ _v_ _ _           _v_ _ _v_ _ _           \n"
        + "  carry16  |             |  carry15|             | carry1  |             | carry0   \n"
        + " <--       |full_adder15 | <--...  | full_adder1 | <--...  | full_adder0 | <--      \n"
        + "           |_ _ _ _ _ _ _|         |_ _ _ _ _ _ _|         |_ _ _ _ _ _ _|          \n"  
        + "             v                       v                       v                      \n"
        + "           newNum[15]               newNum[1]               newNum[0]               \n"
        + "\n"
        + " check for Overflow, newNum[] -> numNumString -> newDecimalNum\n\n");
    
    RippleAdder a1 = new RippleAdder(8);
    RippleAdder a2 = new RippleAdder(8);
    RippleAdder a3 = new RippleAdder(8);
    RippleAdder a4 = new RippleAdder(8);
    RippleAdder a5 = new RippleAdder(8);

    System.out.println(" Inputs   |  Binary  | Decimal | Overflow");
    a1.checkOutputs(50, 58);
    a2.checkOutputs(127, 128);
    a3.checkOutputs(50, 127);
    a4.checkOutputs(-127, 127);
    a5.checkOutputs(20, 127);
    
    RippleAdder a6 = new RippleAdder(16);
    RippleAdder a7 = new RippleAdder(16);
    RippleAdder a8 = new RippleAdder(16);
    RippleAdder a9 = new RippleAdder(16);
    
    System.out.println("\n Inputs        |     Binary      | Decimal | Overflow");
    a6.checkOutputs(50, 600);
    a7.checkOutputs(-1227, 9537);
    a8.checkOutputs(32760, 127);
    a9.checkOutputs(-760,127);

  }

}
