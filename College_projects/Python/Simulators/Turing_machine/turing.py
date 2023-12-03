import pandas as pd

#
# 410 Project 2 Finite Automata
# Luke Clement
#
# This program takes in a text file specified in the instruction pdf.
# It simulates a turing machine and it's transitions in order to
# read in string(s) to be accepted or rejected by the Automata
#
# Run: python3 turing.py
# Provide an input string when prompted. Type "quit" to end program
#

# This is the main class of the program and it performs all of the steps necessary for simulating the Automata
class Automata:
    # Initialize Automata by reading in the file
    def __init__(self):
        self.stateChanges = {}
        self.readInFile()

    # Reads in a properly formatted text file (TM.txt)
    def readInFile(self):
        try:
            # fileName = input("Enter file: ") # file input
            print("Reading... TM.txt")
            with open("TM.txt") as f:
                self.stateNum = f.readline()
                self.haltingState = int(f.readline())
                while True:
                    line = f.readline().split()
                    if not line:
                        break
                    self.stateChanges.update({(line[0],line[1]): (line[2],line[3],line[4])})
        except Exception as e:
            print("Invalid File:" + str(e))
            exit(-1)

    # Main function
    def main(self):
        while True:
            inputString = input("Enter the starting tape with one leading and one trailing blank (_): ") # file input 
            if inputString == "quit":
                print("Goodbye!")
                break
            length = len(inputString)
            inputString = "_"*length + inputString + "_"*length
        
            print("Processing...")
            self.processString('0',length,inputString)

    # Recursive processing of input strings
    def processString(self,state,pos,inputString):
        printString = str(state) + ': ' + inputString[:pos] + "[" + inputString[pos] + "]" +  inputString[pos+1:]
        print(printString,end="")
        if (int(state) == self.haltingState):
            print(" [HALT]")
            return
        
        print()
        try:
            values = self.stateChanges[state,inputString[pos]]
        except:
            print("Invalid transisition. stopping string process. This should only occur with invalid input.")
            return
        inputString =  inputString[:pos] + values[0] +  inputString[pos+1:]
        if values[1] == 'R':
            pos = pos + 1
            if pos == len(inputString)-1:
                inputString = inputString + "__________"
        elif values[1] == 'L':
            pos = pos - 1
            if pos == 0:
                inputString = "__________" + inputString
                pos = pos + 10
        state = str(values[2])
        self.processString(state,pos,inputString)

# Runs the programs
a = Automata()
a.main()