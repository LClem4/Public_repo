import pandas as pd
import csv
import pQueue
import math

# Author: Luke Clement
# This program takes in input from the user and applies the A* algorithm

# Global variables for making the graph
graph_matrix = {}
visited = []

# Class for A_star algorithm
class A_star:
    # Initialization
    def __init__(self, EdgeWeights_fileName, hCost_filename):
        self.load_EdgeWeights_from_csv(EdgeWeights_fileName)
        self.load_hCost_from_csv(hCost_filename) 

    # Adds edge to graph
    def add_edge(self, nodeName, connection, weight):
        if nodeName not in graph_matrix:
            graph_matrix[str(nodeName)] = Node()
        graph_matrix[str(nodeName)].addConnection(connection, weight)

    # Loads Edge weights from csv
    def load_EdgeWeights_from_csv(self, csv_filename):
        with open(csv_filename, mode='r') as file:
            reader = csv.reader(file)
            next(reader)
            for row in reader:
                if len(row) == 3:
                    nodeName, connection, weight = row[0], row[1], row[2]
                    self.add_edge(nodeName, connection, weight)

    # loads hCosts from csv
    def load_hCost_from_csv(self,csv):
        global distance_matrix
        distance_matrix = pd.read_csv(csv, skiprows=6, index_col=0, dtype={'FROM': str})
        distance_matrix.columns = distance_matrix.columns.str.replace(' ', '')
        distance_matrix.dropna(axis = 0, how = 'all', inplace = True)
        

    # Checks if already visited
    def alreadyVisited(self,node): # checks if visited
        for visitedNode in visited:
            if node == visitedNode:
                return True

    # Traverses through the final list
    def traverseList(self,nodePath,startNode,nextNode):  
        nodePath.append(nextNode)
        if not nextNode == startNode:
            self.traverseList(nodePath,startNode,graph_matrix[nextNode].parent)
        else:
            while len(nodePath) > 1:
                node = nodePath.pop(len(nodePath)-1)
                print(str(node) + " - ",end="")
            lastNode = nodePath.pop(0)
            print(str(lastNode))

    # Prints connections in graph of a node (for debugging)
    def printConnections(self,node):
        graph_matrix[node].print()

    # Checks if a node is invalid
    def checkValidNodes(self,node):
        if not node in graph_matrix:
            raise Exception("Invalid node")

    # Prints visited list
    def printVisited(self):
        for node in self.visited:
            print(node)
    
    # main
    def main(self,x,y):
        if x == y:
            print("A* minimum cost path")
            print("[0.0] " + str(x))
        else:
            self.A_star(x,y)

    # Actually runs the A_star algorithm
    def A_star(self,startNode,endNode):
        openList = pQueue.pQueue() # Priority Queue
        openList.insert(startNode,0) # Insert first node
        while not openList.isEmpty():
            node = openList.dequeue()[0] # Only grabs the name
            if node == endNode:
                print("A* minimum cost path")
                print("[" + str(graph_matrix[node].g) + "] ",end="")
                self.traverseList([],startNode,node)
                break
            visited.append(node)
            for child in graph_matrix[node].connections:
                if self.alreadyVisited(child):
                    continue
                if openList.checkIfInList(child):
                    if graph_matrix[child].g > graph_matrix[node].g + graph_matrix[node].connections[child]:
                       graph_matrix[child].setPGH(child,node,endNode)
                       openList.remove(child)
                       openList.insert(child,graph_matrix[child].f())
                else:
                    graph_matrix[child].setPGH(child,node,endNode)
                    openList.insert(child,graph_matrix[child].f())

# Nodes class for storing node data
class Node:
    # Initialization
    def __init__(self, parent=None, g=0, h=0):
        self.parent = parent
        self.connections = {}
        self.g = g  # Cost from start node to this node
        self.h = h  # Heuristic estimate from this node to goal node

    # Adds an edge
    def addConnection(self,connection,weight):
        self.connections[connection] = float(weight)

    # Returns f = g + h
    def f(self):
        return (self.g + self.h)/2

    # Sets parent value
    def setParent(self,parentNode):
        self.parent = parentNode

    # Sets g value
    def setG(self,currentNode,parentNode):
        self.g = graph_matrix[parentNode].g + graph_matrix[parentNode].connections[currentNode]

    # Sets h value
    def setH(self,currentNode,endNode):
        if math.isnan(distance_matrix[currentNode][endNode]):
            self.h = distance_matrix[endNode][currentNode]
        else:
            self.h = distance_matrix[currentNode][endNode]

    # Sets parent, g, and h
    def setPGH(self,currentNode,parentNode,endNode):
        self.setParent(parentNode)
        self.setG(currentNode,parentNode)
        self.setH(currentNode,endNode)

    # Prints node conections
    def print(self):
        for i in self.connections:
            print(self.connections[i])

    # Prints numn of connections
    def num(self):
        return len(self.connections)