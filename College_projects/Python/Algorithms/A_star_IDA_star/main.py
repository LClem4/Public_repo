import pandas as pd
import sys
import A_star
import IDA_star

# Author: Luke Clement
# Main function that is run upon starting program. Takes user input for running A* algorithm

# Driver of the program
class Main:
    # initialization
    def __init__(self):
        self.start()

    # The main function to begin
    def start(self):
        global file # stores file
        global graph # stores the object for performing both searches

        try:
            EdgeWeights_fileName = input("Please enter the edge weight file name and extension: ") # file input
            hCost_filename = input("Please enter the heuristic file name and extension: ") # file input
            print("Loading Files...")
            if sys.argv[1] == "A":
                graph = A_star.A_star(EdgeWeights_fileName,hCost_filename) # A
            elif sys.argv[1] == "IDA":
                graph= IDA_star.IDA_star(EdgeWeights_fileName,hCost_filename) # IDA*
            else:
                raise Exception("format: 'python3 main.py A' or 'python3 main.py IDA' ")
        except: # Throws exceptions if invalid input
            print("invalid file(s) or format")
            exit(-1)
 
        node1 = ""
        node2 = ""
        try:
            node1 = input("Start node : ") # input start node
            node2 = input("End node : ") # input end node    
            graph.checkValidNodes(node1)
            graph.checkValidNodes(node2)
        except: # Throws exceptions if invalid input
            print("invalid node(s)")
            exit(-1)

        try:
            graph.main(node1,node2) # Runs A*
        except: # Throws exceptions if invalid input
            print("Error: Double check that there is no rogue ' ' in the hCosts.csv. I had this problem when I first downloaded hCosts.csv which had a random space in the file.")
            print("If not then... well you really shouldn't be seeing this error. I double checked so not sure why it would do this.")
            exit(-1)

    # Checks over a select group of solutions
    def checkSolutions(self):
        graph = A_star.A_star("EdgeWeights.csv","hCosts.csv")
        graph.main(sys.argv[1],sys.argv[2]) # Runs A*

# Runs Main
main = Main()