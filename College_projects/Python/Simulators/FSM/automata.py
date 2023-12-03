import pandas as pd

#
# 410 Project 1 Automata
# Luke Clement
#
# This program takes in a text file specified in the instruction pdf.
# It simulates a finite state machine and it's transitions in order to
# read in string(s) to be accepted or rejected by the Automata
#

# This is the main class of the program and it performs all of the steps necessary for simulating the Automata
class Automata:
    # Initialize Automata by reading in the file
    def __init__(self):
        self.readInFile()

    # Reads in a properly formatted text file (DFA.txt)
    def readInFile(self):
        try:
            # fileName = input("Enter file: ") # file input
            print("Loading... DFA.txt")
            with open("DFA.txt") as f:
                self.stateNum = f.readline()
                self.acceptingStates = f.readline().split()
                self.transitions = f.readline().split()
                tmpList = []
                while True:
                    line = f.readline().split()
                    if not line:
                        break
                    tmpList.append(line)
                self.stateChanges = pd.DataFrame(tmpList, columns =  self.transitions)
        except:
            print("Invalid File")
            exit(-1)

    # Returns a state change or rejects the input value
    def stateChange(self,state,transition):
        if transition in self.transitions:
            return self.stateChanges[transition][state]
        else:
            return "INPUT INVALID"
        
    # Main function
    def main(self):
        while True:
            inputString = input("Please enter a string to evaluate: ") # file input 
            if inputString == "quit":
                print("Goodbye!")
                break
            else:
                print("Computation...")
                self.processString(0,inputString)

    # Recursive processing of input strings
    def processString(self,state,inputString):
        nextState = self.stateChange(state,inputString[0])
        if nextState == "INPUT INVALID":
            print(str(state) + "," + inputString[0] + " -> INPUT INVALID")
            print("REJECTED")
            return
        elif len(inputString[1:]) == 0:
            print(str(state) + "," + inputString + " -> " + str(nextState) + ",{e}")
            if nextState in self.acceptingStates:
                print("ACCEPTED")
            else:
                print("REJECTED")
            return
        else:
            print(str(state) + "," + inputString + " -> " + str(nextState) + "," + inputString[1:])
            self.processString(int(nextState),inputString[1:])
        return

# Runs the programs
a = Automata()
a.main()