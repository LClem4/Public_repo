import pandas as pd
import sys

# Author: Luke Clement
# Date: 9/6/2022
# This program takes in a file and two numbers as input and performs BFS and DFS operations

# Holds the information for nodes and performing DFS and BFS
class DataList:
    # Builds nodeList
    def buildData(self,fileName,sortedCheck):
        data = pd.read_csv(fileName)
        for index, value in enumerate(data["From"]):
            connections = data.iloc[index][1:].dropna().to_list() # Gets all values after the first, drops na, and makes it a list
            if sortedCheck == "T":
                connections.sort()
            node = Node(value,connections) # Builds a node
            self.nodeList.append(node) # places it in nodeList
  
    # Initialization
    def __init__(self,data,sortedCheck):
        self.nodeList = [] # actual nodes
        self.queue = [] # actual nodes
        self.visited = [] # actual nodes
        self.buildData(data,sortedCheck) # builds nodeList

    # Gets a node by passing in value
    def get(self,nodeValue):
        for node in self.nodeList:
            if nodeValue == node.value:
                return node

    # BFS
    def BFS(self,startValue,endValue):
        self.queue.append(self.get(startValue))
        endNode = self.get(endValue) # Gets endNode
        while True: 
            if len(self.queue) == 0:
                self.clearQueues()
                print("No path exists between " + str(startValue) + " and " + str(endValue))
                return -1
            currentNode = self.queue.pop(0) # Gets front of queue
            if currentNode.value == endNode.value: # checks if endNode
                self.traverseList([],currentNode) # Prints traversal path
                return 0
            self.visited.append(currentNode) # adds current node to visited
            self.searchChildNodesBFS(currentNode) # searches child nodes
                
    # DFS
    def DFS(self,startValue,endValue):
        self.queue.append(self.get(startValue))
        endNode = self.get(endValue) # Gets endNode
        while True:
            if len(self.queue) == 0:
                self.clearQueues()
                print("No path exists between " + str(startValue) + " and " + str(endValue))
                return -1
            currentNode = self.queue[len(self.queue)-1] # Gets top of stack
            self.visited.append(currentNode) # adds current node to visited
            endValueCheck = self.searchChildNodesDFS(currentNode,endNode) # searches child nodes
            if endValueCheck == endValue:
                self.traverseList([],self.queue.pop(len(self.queue)-1)) # Prints traversal path
                return 0

    # BFS searches through child nodes
    def searchChildNodesBFS(self,parentNode): # node is passed in
        for childValue in parentNode.connections: # searches through child nodes
            childNode = self.get(childValue)
            if self.alreadyVisited(childNode): # checks visited
                continue
            else:
                if self.notQueued(childNode): # Adds to queue if not queued already
                    childNode.parentValue = parentNode.value # Tracks parent node
                    self.queue.append(childNode)

    # DFS searches through child nodes
    def searchChildNodesDFS(self,parentNode,endNode): # node is passed in
        for childValue in parentNode.connections: # searches through child nodes
            childNode = self.get(childValue)
            if self.alreadyVisited(childNode): # checks visited
                continue
            else:
                if self.notQueued(childNode): # Adds to queue if not queued already
                    childNode.parentValue = parentNode.value # Tracks parent node
                    self.queue.append(childNode)
                if childNode.value == endNode.value: # checks if endNode
                    return endNode.value  
                return -1
                        
        self.queue.pop(len(self.queue)-1)
        return -1
    
    # Checks if already visited
    def alreadyVisited(self,node): # checks if visited
        for visitedNode in self.visited:
            if node.value == visitedNode.value:
                return True

    # Checks if already queued
    def notQueued(self,node): # checks if visited
        for queuedNode in self.queue:
            if node.value == queuedNode.value:
                return False
        return True

    # Traverses through the final list
    def traverseList(self,nodePath,endNode):  
        nodePath.append(endNode.value)
        if not (endNode.parentValue == -1):
            self.traverseList(nodePath,self.get(endNode.parentValue))
        else:
            #self.printQueue()
            self.clearQueues()
            while len(nodePath) > 1:
                node = nodePath.pop(len(nodePath)-1)
                print(str(node) + " - ",end="")
            lastNode = nodePath.pop(0)
            print(str(lastNode))

    # Clears queues
    def clearQueues(self):
        self.queue.clear()
        self.visited.clear()
        for i in range(len(self.nodeList)):
            self.nodeList[i].parentValue = -1

    # Prints list
    def printList(self):
        for node in self.nodeList:
            print(node.value)
            print(node.connections)

    # Prints queue
    def printQueue(self):
        for node in self.queue:
            print(node.value)
    
    # Prints visited
    def printVisited(self):
        for node in self.visited:
            print(node.value)
    
    # gets first and last node values
    def getEndValues(self):
        return self.nodeList[0].value, self.nodeList[len(self.nodeList)-1].value
    
    # main
    def main(self,x,y):
        if x == y:
            print("Breath-first traversal:")
            print(x)
            print("Depth-first search:")
            print(x)
        else:
            #self.printList()
            print("Breath-first traversal:")
            self.BFS(x,y)
            print("Depth-first search:")
            self.DFS(x,y)
            

# Class for storing node data
class Node:
    # initialization
    def __init__(self,value,connections):
        self.value = value # number not actual node
        self.parentValue = -1 # number not actual node
        self.connections = connections # number not actual node

# Main function that is run upon starting program. Takes user input
class Main:
    # initialization
    def __init__(self):
        self.start()

    # The main function to begin
    def start(self):
        global file # stores file
        global DataNodes # stores the object for performing both searches
        file = input("Please enter the file name and extension: ") # file input
        try:   
            sortedCheck = input("Sort the edges? : T/F ") # input start node
            print("Loading file...")
            DataNodes = DataList(file,sortedCheck) # 'BFS_DFS.csv'
        except: # Throws exceptions if invalid input
            print("There was a error: invalid file/input")
            exit(1)
        minNum, maxNum = DataNodes.getEndValues() # min and max value nodes
        number1 = -1
        number2 = -1
        sortedNodes = "F"
        try:
            number1 = int(input("Start node : ")) # input start node
            number2 = int(input("End node : ")) # input end node    
            self.checkValidNodes(number1,DataNodes.nodeList)
            self.checkValidNodes(number2,DataNodes.nodeList)
        except: # Throws exceptions if invalid input
            print("invalid number(s)")
            exit(-1)
        DataNodes.main(number1,number2) # Runs both BFS and DFS

    # Checks if inputs are valid
    def checkValidNodes(self,number,nodeList):
        for node in nodeList:
            if number == node.value:
                return True
        exit(-1)

    # Checks over a select group of solutions
    def checkSolutions(self):
        DataNodes = DataList("test1.csv","T") # 'BFS_DFS.csv'
     
        for i in range(1,20):
            for j in range(1,20):
                if i == 10 or j == 10:
                    continue
                print(i,j)
                DataNodes.main(i,j)

# Runs Main
main = Main()