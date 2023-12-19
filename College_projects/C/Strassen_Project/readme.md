# Project Information
  CSC310 Algorithms
  Author: Luke Clement
  Professor: Dr. Armstrong
  File Created: April 5th, 2023
  File Updated: Decemember 1st, 2023

# Description
	Takes input from a file and performs matrix multiplication based on that input using a recursive method called strassen and the normal method. Both methods are 
  then combined at a certain cross over point where strassen is better with larger matrices but regular matrix multiplication is better with smaller ones.

# Platform
	anywhere you can run c should work

# Language
	C

# Files Included
	Sources: strassen.c

	Extra:
		 README
		 Makefile

	Data input: 
     	matricesN12/Random_Matrix_0.csv through matricesN12/Random_Matrix_39.csv (Test matrices)
		 "example.csv" (Given test matrix)

	Data output:
		 Tests/Tests/N10output.csv

	Results:
		 Tests/strassen.xlsx
		 
# How to Compile
	1. make or
	2. gcc -o strassen strassen.c and gcc -O2 -o strassenO2 strassen.c

# How to Run Dr. Armstrong's matrices
  0. ./strassen Test    // (Use this) Prints output times for bruteforce, strassen, and strassen + bruteforce
  1. ./strassen Test -p // (or this) Prints output times and output matrices
  2. All of the commands above can also be run using ./strassenO2 instead of ./strassen (which will use O2 compiler optimizations)

  Also note that the file that is passed in is "example.csv" DO NOT CHANGE THE NAME UNLESS YOU HAVE TO. But if you do, then you must change
  the file name by where it says " // Dr. Armstrong input file" in main. The program can only test your 32x32 matrix that you gave us and no other multiple of 2. 
  You should be fine to edit the values in the 32x32 matrix in the file. Other things to note: You can change the output file in main where it says "// NAME OF FILE TO BE OUTPUT."
  but you probably won't need to do that since you will probably just run './strassen Test' or .'/Strassen Test -p' unless you want to wait a couple minutes for it to run
  20 matrix pairs. './strassen' runs all 20 pairs.

# How to Run normally
  1. ./strassen         // (Probably Don't use this) Runs at n = 10, It takes in 5 Pairs of matrices and outputs the time of bruteforce, strassen, and strassen + bruteforce and a csv file
  2. ./strassen a       // (Use this) Example: ./strassen 9 multiplies a single matrix pair at n = 9. Prints the time of bruteforce, strassen, and strassen + bruteforce
  3. ./strassen a b     // (or this) Does the same thing as #2 except that the second argument changes the cutoff size. 2 <= b <= n. Example: ./strassen 9 5
  4. ./strassen a b -p  // (or this) Does the same thing as #3 except that the third argument is a print flag. if -p is in the third argument then the end result matrices will be printed.
  5. All of the commandss above can also be run using ./strassenO2 instead of ./strassen which will use O2 compiler optimizations
  6. ./straseen a b -p
 
  For my project I used ./strassenO2 because it was faster. Also, when you run a single matrix pair, it defaults to Random_Matrix_0 and Random_Matrix_1. You can change the input matrices
  by going into main and changing the file number where it says // DETERMINES WHICH MATRIX FILE IS RUN. For example char *file1 = getFile(7); will run matrix 7.

# Known Bugs
	Make sure to stay with the formatting and it should be fine.

# Algorithm
	This uses the Strassen algorithm to perform matrix multiplication.