package adder;

/**
 * A class to mimic logic gates
 */
public class Gate {

	public int AND(int A, int B) {
		if(A == 1 && B == 1) {
			return 1;
		}
		return 0;
	}
	
	public int OR(int A, int B) {
		if(A == 1 || B == 1) {
			return 1;
		}
		return 0;
	}
	public int OR(int A, int B, int C) {
		if(A == 1 || B == 1 || C == 1) {
			return 1;
		}
		return 0;
	}
	
	public int NOT(int A) {
		if(A == 0) {
			return 1;
		}
		return 0;
	}
	
	public int XOR(int A, int B) {
		if(A == 1 && B == 0) {
			return 1;
		}
		else if(A == 0 && B == 1) {
			return 1;
		}
		return 0;
	}
	
	public int NXOR(int A, int B) {
		if(A == 0 && B == 0) {
			return 1;
		}
		else if(A == 1 && B == 1) {
			return 1;
		}
		return 0;
	}
	
	public int NAND(int A, int B) {
		if(A == 1 && B == 1) {
			return 0;
		}
		return 1;
	}
	
	public int NOR(int A, int B) {
		if(A == 0 && B == 0) {
			return 1;
		}
		return 0;
	}
	
	public void testGates() {
		
		Gate gate = new Gate();
		
		System.out.println("AND");
		System.out.println("A B X");
		for(int i = 0; i < 2; i++) {
		for(int j = 0; j < 2; j++) {
			System.out.println(i + " " + j + " " + gate.AND(i,j));
		
		}
		}
		
		System.out.println();
		
		System.out.println("OR");
		System.out.println("A B X");
		for(int i = 0; i < 2; i++) {
		for(int j = 0; j < 2; j++) {
			System.out.println(i + " " + j + " " + gate.OR(i,j));
		
		}
		}
		
		System.out.println();
		
		System.out.println("NOT");
		System.out.println("A X");
		for(int i = 0; i < 2; i++) {
			System.out.println(i + " " + gate.NOT(i));
		
		}
		
		System.out.println();
		
		System.out.println("NAND");
		System.out.println("A B X");
		for(int i = 0; i < 2; i++) {
		for(int j = 0; j < 2; j++) {
			System.out.println(i + " " + j + " " + gate.NAND(i,j));
		
		}
		}
		
		System.out.println();
		
		System.out.println("NOR");
		System.out.println("A B X");
		for(int i = 0; i < 2; i++) {
		for(int j = 0; j < 2; j++) {
			System.out.println(i + " " + j + " " + gate.NOR(i,j));
		
		}
		}
		
		System.out.println();
		
		System.out.println("XOR");
		System.out.println("A B X");
		for(int i = 0; i < 2; i++) {
		for(int j = 0; j < 2; j++) {
			System.out.println(i + " " + j + " " + gate.XOR(i,j));
		
		}
		}
		
		System.out.println();
		
		System.out.println("NXOR");
		System.out.println("A B X");
		for(int i = 0; i < 2; i++) {
		for(int j = 0; j < 2; j++) {
			System.out.println(i + " " + j + " " + gate.NXOR(i,j));
		
		}
		}
	
		System.out.println();
	}
	
}
